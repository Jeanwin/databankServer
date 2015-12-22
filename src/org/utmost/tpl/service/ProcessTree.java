package org.utmost.tpl.service;

import java.util.HashMap;
import java.util.List;

/**
 * TPL Tree handle class
 * 
 * @author wanglm
 * 
 */
public class ProcessTree {
	private List<HashMap<String, Object>> list = null;
	private String type = null;
	/**
	 * initial list and type
	 * @param _list
	 * @param _type
	 */
	public ProcessTree(List<HashMap<String, Object>> _list, String _type) {
		list = _list;
		type = _type;
	}
	/**
	 * search root
	 * @return
	 */
	private HashMap<String, Object> searchRoot() {
		HashMap<String, Object> root = null;
		for (HashMap<String, Object> map : list) {
			Object obj = map.get("nodelevel");
			if (obj != null && obj.toString().equals("0")) {
				root = map;
				System.out.println("root:" + root);
			}
		}
		return root;
	}
	/**
	 * recursive get tree organization
	 * @param uuid
	 * @param sb
	 * @return
	 */
	private StringBuffer process(String uuid, StringBuffer sb) {
		for (HashMap<String, Object> map : list) {
			Object pid = map.get("pid");
			if (pid != null) {
				if (((String) pid).equals(uuid) && type.equals("data")) {
					sb.append("<node uuid=\"" + (String) map.get("uuid")
							+ "\" pid=\"" + (String) map.get("pid")
							+ "\" nodename=\"" + (String) map.get("nodename")
							+ "\" nodecode=\"" + (String) map.get("nodecode")
							+ "\" tablename=\"" + (String) map.get("tablename")
							+ "\" allowview=\"" + (String) map.get("allowview")
							+ "\" nodedesc=\"" + (String) map.get("nodedesc")
							+ "\" nodetype=\"" + (String) map.get("nodetype")
							+ "\" nodelevel=\"" + (String) map.get("nodelevel")
							+ "\" state=\"" + (String) map.get("state") + "\">"
							+ "\n");
					this.process((String) map.get("uuid"), sb);
					sb.append("</node>" + "\n");
				} else if (((String) pid).equals(uuid) && type.equals("view")) {  //view
					String allowview = (String) map.get("allowview");
					String nodetype = (String) map.get("nodetype");
					if (allowview.equals("true")) {
						sb.append("<node uuid=\"" + (String) map.get("uuid")
								+ "\" pid=\"" + (String) map.get("pid")
								+ "\" nodename=\"" + (String) map.get("nodename")
								+ "\" nodecode=\"" + (String) map.get("nodecode")
								+ "\" tablename=\"" + (String) map.get("tablename")
								+ "\" allowview=\"" + (String) map.get("allowview")
								+ "\" nodedesc=\"" + (String) map.get("nodedesc")
								+ "\" nodetype=\"" + (String) map.get("nodetype")
								+ "\" nodelevel=\"" + (String) map.get("nodelevel") 
								+ "\" state=\"" + (String) map.get("state") + "\">" + "\n");
						this.process((String) map.get("uuid"), sb);
						sb.append("</node>" + "\n");
					}
				}
			}
		}
		// System.out.println(sb.toString());
		return sb;
	}
	/**
	 * generate tree organization
	 * @return
	 */
	public String toTree() {
		HashMap<String, Object> map = this.searchRoot();
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			sb.append("<node uuid=\"" + (String) map.get("uuid") + "\" pid=\""
					+ (String) map.get("pid") + "\" nodename=\""
					+ (String) map.get("nodename") + "\" nodecode=\""
					+ (String) map.get("nodecode") + "\" tablename=\""
					+ (String) map.get("tablename") + "\" allowview=\""
					+ (String) map.get("allowview") + "\" nodedesc=\""
					+ (String) map.get("nodedesc") + "\" nodetype=\""
					+ (String) map.get("nodetype") + "\" nodelevel=\""
					+ (String) map.get("nodelevel") + "\" state=\""
					+ (String) map.get("state") + "\">" + "\n");
			sb = this.process((String) map.get("uuid"), sb);
			sb.append("</node>" + "\n");
			return sb.toString();
		} else {
			sb.append("<node uuid=\"" + "null" + "\" pid=\"" + "null"
					+ "\"  nodename=\"" + "none" + "\" nodetype=\"" + "null"
					+ "\">" + "\n");
			sb.append("</node>");
			return sb.toString();
		}
	}
}
