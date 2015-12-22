package org.utmost.tpl.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;
import org.utmost.common.SpringContext;
import org.utmost.util.PathUtil;


/**
 * TPL :config data kernel class and need  optimization
 * 
 * @author wanglm
 * 
 */
@Service("TemplateService")
public class TemplateService extends CommService {
	private static Logger logger = Logger.getLogger(TemplateService.class);

	/**
	 * find all from U_TPL_TEMPLATE
	 * @return
	 */
	public List findAll() {
		return getDb().findAll("U_TPL_TEMPLATE");
	}
	/**
	 * find by node uuid
	 * @param uuid
	 * @return
	 */
	public List findByUUID(String uuid) {
		return getDb().findByHql("from U_TPL_TEMPLATE v where v.uuid='" + uuid + "'",false);
	}
	/**
	 * find by parent uuid
	 * @param pid
	 * @return
	 */
	public List findByPID(String pid) {
		return getDb().findByHql("from U_TPL_TEMPLATE v where v.pid='" + pid + "'",false);
	}

	/**
	 * generate xml tree 
	 * data mode
	 * @param list
	 * @param type
	 * @return
	 */
	public String processTreeForData() {
		this.findAll();
		ProcessTree p = new ProcessTree(this.findAll(), "data");
		String xml = p.toTree();
		// System.out.println(xml);
		return xml;
	}
	/**
	 * generate xml tree
	 * view mode
	 * @return
	 */
	public String processTreeForView() {
		this.findAll();
		ProcessTree p = new ProcessTree(this.findAll(), "view");
		String xml = p.toTree();
		// System.out.println(xml);
		return xml;
	}
	/**
	 * delete data by uuid
	 * @param uuid
	 */
	public void deleteByUUID(String uuid) {
		System.out.println("deleteByUUID:" + uuid);
		List<HashMap<String, Object>> list = this.findByUUID(uuid);
		for (HashMap<String, Object> map : list) {
			// delete data table
			getDb().deleteByHql("from U_TPL_TEMPLATEDATA v where v.cid='" + uuid + "'");
			// delete view table
			getDb().deleteByHql("from U_TPL_TEMPLATEVIEW v where v.cid='" + uuid + "'");
			// default
			this.deleteByPID(uuid);
			getDb().delete(map);
		}
	}
	/**
	 * delete data by parent uuid
	 * @param pid
	 */
	private void deleteByPID(String pid) {
		List<HashMap<String, Object>> list = this.findByPID(pid);
		for (HashMap<String, Object> map : list) {
			Object obj = map.get("uuid");
			if (obj != null) {
				this.deleteByUUID((String) obj);
			}
			getDb().delete(map);
		}
	}
	/**
	 * find tpl data by uuid
	 * @param uuid
	 * @return
	 */
	public List findTplDataByUUID(String uuid) {
		return getDb().findByHql("from U_TPL_TEMPLATEDATA v where v.cid='" + uuid + "'",false);
	}

	/**
	 * generate and copy hbm file
	 * 
	 * @return
	 */
	public String processAllHbm() {
		String hql = "from U_TPL_TEMPLATE v where v.nodetype='Collection'";
		List<HashMap> list = getDb().findByHql(hql,false);
		for (HashMap<Object, Object> map : list) {
			String uuid = (String) map.get("uuid");
			try {
				this.processHbm(uuid);
			} catch (Exception e) {
				e.printStackTrace();
				return "fault";
			}
		}
		return "success";
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	public String processHbm(String uuid) throws Exception {
		ProcessHbm o = new ProcessHbm();
		List list = this.findTplDataByUUID(uuid);
		List tlist = this.findByUUID(uuid);
		if (tlist.size() != 0) {
			HashMap m = (HashMap) tlist.get(0);
			String tablename = (String) m.get("tablename");
			if (list.size() < 2) {
				logger.fatal(tablename + " :table data fatal");
				return null;
			}
			String xml = o.process(list, tablename.toUpperCase());
			// write file
			try {
				File dir = new File("../tmphbm/");
				if (!dir.exists())  //if file isn't exist
					dir.mkdirs();
				String file = dir.getCanonicalPath() + "/";
				String filename = file + tablename + ".hbm.xml";  //obtain hibernate file name
				System.out.println("filename:" + filename);
				FileOutputStream fos = new FileOutputStream(filename);
				fos.write(xml.getBytes("UTF-8"));   //write xml string to hibernate file 
				fos.close();
				String system = System.getProperty("os.name");
				System.out.println("os.name:" + system);
				if (system.indexOf("Windows") >= 0) {
					// copy file to classes
					this.cmdCopy(file, tablename);
				} else {
					// handle Linux
					// this.cpCopy(file, tablename);//non implement
					throw new Exception("暂时不支持此操作系统");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return xml;
		}
		return null;
	}

	/**
	 * use cmd copy file in window stage
	 * 
	 * @param file
	 * @param tablename
	 * @throws IOException
	 */
	private void cmdCopy(String file, String tablename) throws IOException {
		String copyfrom = file;
		copyfrom = copyfrom.replaceAll("/", "\\\\");
		copyfrom = copyfrom.replaceAll("\\\\", "\"\\\\\"");
		copyfrom = copyfrom.replaceFirst("\"", "");
		copyfrom = copyfrom.substring(0, copyfrom.length() - 1);
		copyfrom = copyfrom + tablename + ".hbm.xml";

		String copyto = TemplateService.class.getResource("/").toString();
		copyto = copyto.replaceAll("file:/", "");
		copyto = copyto.replaceAll("/", "\"\\\\\"");
		copyto = copyto.replaceAll("%20", " ");
		copyto = copyto.replaceFirst("\"", "");
		copyto = copyto.substring(0, copyto.length() - 1);
		copyto = copyto + "org\\utmost\\hbm\\";

		Runtime rt = Runtime.getRuntime();
		String cmd = "cmd /c copy /y " + copyfrom + " " + copyto;
		System.out.println(cmd);
		rt.exec(cmd);
	}

	/**
	 * copy file in linux or unix
	 * 
	 * @param file
	 * @param tablename
	 * @throws IOException
	 */
	private void cpCopy(String file, String tablename) throws IOException {}
}
