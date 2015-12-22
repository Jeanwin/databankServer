package com.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.util.DateUtil;

import com.vgc.databank.util.CommonTools;
import com.vgc.databank.util.DownloadListUtil;

/**
 * 
 * @author zhangwei
 * 
 */
@Service("SearchService")
public class SearchService {

	private static Logger logger = Logger.getLogger(SearchService.class);

	@Autowired
	private AutoService autoService;

	@Autowired
	private DataInfoService dataInfoService;
	
	@Autowired
	private DownloadListUtil downloadListUtil;

	/**
	 * 
	 * @description  search By Keyword
	 * @param currentPage
	 * @param pageSize
	 * @param keyword
	 * @param andOr
	 * @param hasCategory
	 * @param validNodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<HashMap> searchByKeyword(int currentPage, int pageSize,
            String keyword,String andOr, boolean hasCategory, String validNodes) {
	    String[] keyWordArr = keyword.split(" ");
        logger.debug("keyword: " + keyword);
        if (!"".equals(keyword.trim())) {
            StringBuffer hql = new StringBuffer("select new map(b.uuid as uuid, b.title_en as title_en , b.title_ch as title_ch ,x.funcname as funcname ,x.uuid as rootuuid," +
            "n.uuid as nodeuuid,COALESCE(b.standard_attribute1, '')||COALESCE(b.standard_attribute2, '')||COALESCE(b.standard_attribute3, '')||' '||COALESCE(b.no_, '')||'-'||COALESCE(b.version_, '') as standard_no ) " +
            "from U_PORTAL_FUNC n ,U_PORTAL_FUNC x ,B_COMMONDATA b " +
            "where b.func_uuid = n.uuid and n.classuuid = x.uuid and ") ;
            hql.append(constructAndOrSql(keyWordArr,andOr));
            
            if(hasCategory){
              hql.append( " and b.func_uuid in (" + validNodes + ") ");
            }
            
            hql.append( "  order by COALESCE(b.standard_attribute1, '') ,COALESCE(b.standard_attribute2, '') ,COALESCE(b.standard_attribute3, '')  ,b.no_ ,b.version_   ");
            List<HashMap> listData = (List<HashMap>) autoService.pagination(currentPage,
                    pageSize, hql.toString());
            return listDataParse(listData);
        }
        return null;
	}
	/**
	 * construct simple search keyword for columns
	 * 
	 * @param keyWordArr
	 * @param andOr
	 * @return
	 */
	private String constructAndOrSql(String[] keyWordArr,String andOr){
    	 StringBuffer sb = new StringBuffer("(");
    	 sb.append(constructAndOrSubSql(keyWordArr,"b.title_en",andOr)+" or " +
    	           constructAndOrSubSql(keyWordArr,"b.title_ch",andOr)+" or " +
    	           constructAndOrSubSql(keyWordArr,"b.key_words",andOr)+")") ;
	    return sb.toString();
	}
	/**
	 * construct sql for AND or OR
	 * 
	 * @param keyWordArr
	 * @param columnStr
	 * @param andOr
	 * @return
	 */
	private String constructAndOrSubSql(String[] keyWordArr,String columnStr,String andOr){
	    String subStr = "(";
  	    for(int i=0 ; i<keyWordArr.length ; i++){
  	        subStr += " UPPER("+columnStr+") like '%"+keyWordArr[i].trim().toUpperCase()+"%' "+andOr;
        }
  	    return subStr.substring(0,subStr.lastIndexOf(andOr))+")";
	}
	/**
	 * parse data of list
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
    private List<HashMap> listDataParse(List<HashMap> list) {
        List<HashMap> listData = new ArrayList<HashMap>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                HashMap hm = list.get(i);
                String filePath = downloadListUtil.getPath((String) hm.get("nodeuuid"),"","/");
                hm.put("nodeuuid_", filePath);
                listData.add(hm);
            }
        }
        return listData;
    }
	
	/**
	 * 
	 * @description search By Keyword Total Rows
	 * @param keyword
	 * @param andOr
	 * @param hasCategory
	 * @param validNodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long searchByKeywordTotalRows(String keyword,String andOr, boolean hasCategory, String validNodes) {
		long totalRows = 0;
		String[] keyWordArr = keyword.split(" ");
		logger.debug("keyword: " + keyword);
		List<HashMap> listData = null;
		if (!"".equals(keyword.trim())) {
			String hql = "select new map( count(*) as totalRows ) from U_PORTAL_FUNC n ,U_PORTAL_FUNC x ,B_COMMONDATA b " +
					"where b.func_uuid = n.uuid and n.classuuid = x.uuid and " ;
			hql+= constructAndOrSql(keyWordArr,andOr);
			if(hasCategory){
				hql += " and  b.func_uuid in (" + validNodes + ") ";
			}
			listData = (List<HashMap>) autoService.findByHql(hql);
			if (listData != null) {
				totalRows = (Long) listData.get(0).get("totalRows");
			}
		}
		return totalRows;
	}


	/**
	 * advanced search
	 * 
	 * @param node_UUID
	 * @param main_func_UUID
	 * @param hqlParam
	 * @param user_UUID
	 * @param pageNo
	 * @param pageSize
	 * @param hasProtected
	 * @param show_mode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> advancedSearch(String node_UUID,String main_func_UUID, String hqlParam,
			String user_UUID, int pageNo, int pageSize, boolean hasProtected,long show_mode) {
		hqlParam = hqlParam.trim();
		logger.debug("get fieldtitleinfo method param: ( main_func_UUID:"
				+ main_func_UUID + " )," + "( func_UUID:" + node_UUID
				+ " )," + "( pageNo:" + pageNo + " )," + "( pageSize:"
				+ pageSize + " )," + "(user_UUID:" + user_UUID + " ),"
				+ "( hasProtected:" + hasProtected + " )," + "( show_mode:"
				+ show_mode + " )");
		List<HashMap> listData = null;
		/**
		 * if doesn't select items on advanced search screen, return listData is null.
		 * doesn't display data in result screen
		 */
		if ("".equals(hqlParam)) {  
			return listData;
		}
		StringBuffer hql = this.buildHql(node_UUID, main_func_UUID, hqlParam, user_UUID, hasProtected, show_mode);
		if(hql != null){
			listData = (List<HashMap>) autoService.pagination(pageNo, pageSize, hql.toString());
		}
		if (listData != null) {
			logger.debug("listData size: " + listData.size());
		} else {
			logger.debug("listData size: 0");
		}
		return transferDate(listData);
	}
	
	public List<HashMap> advancedSearch(String funcArr, String hqlParam,
			String user_UUID, int pageNo, int pageSize, boolean hasProtected,long show_mode) {
		hqlParam = hqlParam.trim();
		logger.debug("get fieldtitleinfo method param: ( pageNo:" + pageNo + " )," + "( pageSize:"
				+ pageSize + " )," + "(user_UUID:" + user_UUID + " ),"
				+ "( hasProtected:" + hasProtected + " )," + "( show_mode:"
				+ show_mode + " )");
		List<HashMap> listData = null;
		/**
		 * if doesn't select items on advanced search screen, return listData is null.
		 * doesn't display data in result screen
		 */
		if ("".equals(hqlParam)) {  
			return listData;
		}
		StringBuffer hql = this.buildHql(funcArr, hqlParam, user_UUID, hasProtected, show_mode);
		if(hql != null){
			listData = (List<HashMap>) autoService.pagination(pageNo, pageSize, hql.toString());
		}
		if (listData != null) {
			logger.debug("listData size: " + listData.size());
		} else {
			logger.debug("listData size: 0");
		}
		return transferDate(listData);
	}
	
	/**
	 * Advanced search by criteria and no pagination
	 *  
	 * @param node_UUID
	 * @param main_func_UUID
	 * @param hqlParam
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * @return
	 */
	public List<HashMap> advancedSearch(String node_UUID,String main_func_UUID, String hqlParam,
			String user_UUID, boolean hasProtected,long show_mode) {
		hqlParam = hqlParam.trim();
		logger.debug("get fieldtitleinfo method param: ( main_func_UUID:"
				+ main_func_UUID + " )," + "( func_UUID:" + node_UUID
				+ " ),"  + "(user_UUID:" + user_UUID + " ),"
				+ "( hasProtected:" + hasProtected + " )," + "( show_mode:"
				+ show_mode + " )");
		List<HashMap> listData = null;
		/**
		 * if doesn't select items on advanced search screen, return listData is null.
		 * doesn't display data in result screen
		 */
		if ("".equals(hqlParam)) {
			return listData;
		}
		StringBuffer hql = this.buildHql(node_UUID, main_func_UUID, hqlParam, user_UUID, hasProtected, show_mode);
		if(hql != null){
			listData = (List<HashMap>) autoService.findByHql(hql.toString());
		}
		
		return transferDate(listData);
	}
	
	
	/**
	 * transfer date format
	 * 
	 * @param listData
	 * @return
	 */
	private List<HashMap> transferDate(List<HashMap> listData){
		listData = (List<HashMap>) dataInfoService.listDataParseNo(listData);
		List<String> keyStr = new ArrayList<String>();  //date format need to modify
		keyStr.add("last_modify_date_str");
		keyStr.add("issuance_date_str");
		keyStr.add("execute_date_str");
		keyStr.add("pre_execute_date_str");
		keyStr.add("online_auto_execute_date_str");
		keyStr.add("publication_date_str");
		DateUtil.transferStrDate(listData, keyStr);
		return listData;
	}
	/**
	 * make hql string
	 * 
	 * @param node_UUID
	 * @param main_func_UUID
	 * @param hqlParam
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * @return
	 */
	private StringBuffer buildHql(String node_UUID,String main_func_UUID, String hqlParam,
			String user_UUID, boolean hasProtected,long show_mode){
		StringBuffer hql = null;
		
		String[] fieldName = dataInfoService.getFieldName(main_func_UUID,
				node_UUID, user_UUID, hasProtected, show_mode);
		String[] leafArr = dataInfoService.getSubFunc_UUID(node_UUID,user_UUID);
		
		if (leafArr != null) {
			logger.debug("leafArr size: " + leafArr.length);
			hql = new StringBuffer();
			if (StaticFunUUID.standardUUID.equals(main_func_UUID)) {
				hql.append("select new map( ");
				hql.append("g.govenor_code as govenor_code , t.technical_committees_code as technical_committees_code,");
				hql.append("b.uuid as uuid ,");
				hql.append("b.last_modify_author as last_modify_author , ");
				hql.append("b.func_uuid as func_uuid , ");
				for (int i = 0; i < fieldName.length; i++) {
					hql.append("b.").append(fieldName[i].toLowerCase()).append(
							"  as  ").append(fieldName[i].toLowerCase())
							.append(" ,");
				}
				hql.deleteCharAt(hql.length() - 1)
						.append(" ) from B_COMMONDATA as b , B_GOVENOR_CODE as g, B_TECHNICAL_COMMITTEES_CODE as t")
						.append(" where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(")");
				hql.append("and b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid ")
						.append(hqlParam+"  order by COALESCE(b.standard_attribute1, '') ,COALESCE(b.standard_attribute2, '') ,COALESCE(b.standard_attribute3, '')  ,b.no_ ,b.version_   ");
				logger.info("getData:  " + hql.toString() + ", mainFunc: " + main_func_UUID);
			} else {
				hql.append("select new map( ");
				for (int i = 0; i < fieldName.length; i++) {

					hql.append("b.uuid as uuid").append(" ,");
					hql.append("b.").append(fieldName[i].toLowerCase()).append(
							"  as  ").append(fieldName[i].toLowerCase()).append(" ,");
				}
				hql.deleteCharAt(hql.length() - 1).append(" ) from B_COMMONDATA as b ").append(" where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(") ").append(hqlParam);
				hql.append("  order by COALESCE(b.standard_attribute1, '') ,COALESCE(b.standard_attribute2, '') ,COALESCE(b.standard_attribute3, '')  ,b.no_ ,b.version_   ");
				logger.info("getData:  " + hql.toString() + ", mainFunc: " + main_func_UUID);
			}
		}
		return hql;
	}
	
	private StringBuffer buildHql(String funcArr, String hqlParam,
			String user_UUID, boolean hasProtected,long show_mode){
		StringBuffer hql = null;
		String[] leafArr = null;
		if(funcArr!=null){
			leafArr = funcArr.split(",");
		}
		if (leafArr != null) {
			logger.debug("leafArr size: " + leafArr.length);
			hql = new StringBuffer();
				hql.append("select new map( ");
				hql.append("b.uuid as uuid ,");
				hql.append("b.last_modify_author as last_modify_author , ");
				hql.append("b.func_uuid as func_uuid , ");
			    hql.append("b.title_ch as title_ch,");
			    hql.append("b.title_en as title_en,");
			    hql.append("b.execute_date_str as execute_date_str,");
			    hql.append("b.online_auto_execute_date_str as online_auto_execute_date_str,");
				hql.append("b.standard_attribute1 as standard_attribute1 , ");
				hql.append("b.standard_attribute2 as standard_attribute2 , ");
				hql.append("b.standard_attribute3 as standard_attribute3 , ");
				hql.append("b.no_ as no_ , ");
				hql.append("b.version_ as version_ ,");
				hql.deleteCharAt(hql.length() - 1)
						.append(" ) from B_COMMONDATA as b where");
				if(leafArr!=null){
					hql.append("  b.func_uuid in ( ");
					for (int i = 0; i < leafArr.length; i++) {
						hql.append("").append(leafArr[i]).append(",");
					}
					hql.deleteCharAt(hql.length() - 1).append(")");
				}
				hql//.append("and b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid ")
						.append(hqlParam+"  order by COALESCE(b.standard_attribute1, '') ,COALESCE(b.standard_attribute2, '') ,COALESCE(b.standard_attribute3, '')  ,b.no_ ,b.version_   ");
				logger.info("getData:  " + hql.toString() + ", func: " + funcArr);
			
		}
		return hql;
	}
	
	
	
	/**
	 * total count of advanced search
	 * 
	 * @param main_func_UUID
	 * @param hqlParam
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long advancedSearchTotalRows(String funcArr,
			String hqlParam, String user_UUID, boolean hasProtected,
			long show_mode) {
		long totalRows = 0;
		hqlParam = hqlParam.trim();
		List<HashMap> listData = null;
		if (!"".equals(hqlParam)) {
			String[] leafArr = null;
			if(funcArr==null){
			   leafArr = null;
			}else{
				leafArr = funcArr.split(",");
			}
			StringBuffer hql = null;
			if (leafArr != null) {
				logger.debug("leafArr size: " + leafArr.length);
				hql = new StringBuffer();
					hql.append("select new map( count(*) as totalRows ) ");
					hql.append(" from B_COMMONDATA as b where");
					if(leafArr!=null){
						hql.append("  b.func_uuid in ( ");
						for (int i = 0; i < leafArr.length; i++) {
							hql.append("").append(leafArr[i]).append(",");
						}
						hql.deleteCharAt(hql.length() - 1).append(")");
					}
				hql/*.append(" b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid ")*/.append(hqlParam);
				listData = (List<HashMap>) autoService.findByHql(hql.toString());
				if(listData!=null){
					totalRows=(Long)listData.get(0).get("totalRows");
				}
			}
		}
		return totalRows;
	}
	/*public long advancedSearchTotalRows(String node_UUID,String main_func_UUID,
			String hqlParam, String user_UUID, boolean hasProtected,
			long show_mode) {
		CommonTools.getLengendsValues();
		long totalRows = 0;
		hqlParam = hqlParam.trim();
		List<HashMap> listData = null;
		if (!"".equals(hqlParam)) {
			String[] leafArr = dataInfoService.getSubFunc_UUID(node_UUID,
					user_UUID);
			StringBuffer hql = null;
			if (leafArr != null) {
				logger.debug("leafArr size: " + leafArr.length);
				hql = new StringBuffer();
				if (StaticFunUUID.standardUUID.equals(main_func_UUID)) {
					hql.append("select new map( count(*) as totalRows ) ");
					hql.append(" from B_COMMONDATA as b , B_GOVENOR_CODE as g, B_TECHNICAL_COMMITTEES_CODE as t")
					   .append(" where b.func_uuid in ( ");
					for (int i = 0; i < leafArr.length; i++) {
						hql.append("'").append(leafArr[i]).append("',");
					}
					hql.deleteCharAt(hql.length() - 1).append(")");
				hql.append("and b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid ").append(hqlParam);
				} 
				listData = (List<HashMap>) autoService.findByHql(hql.toString());
				if(listData!=null){
					totalRows=(Long)listData.get(0).get("totalRows");
				}
			}
		}
		return totalRows;
	}*/
	
	/**
	 * @see get all the legends 
	 * @return
	 */
	public List<String> getLegends(){
		return CommonTools.getLengendsValues();
	}
}
