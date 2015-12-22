/**
 * 2015-2-2 10:17:19
 * This class is used for category
 */
package org.utmost.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;
import org.utmost.common.DBSupport;
import org.utmost.tpl.service.TemplateService;

import com.vgc.databank.util.Constant;

/**
 * @author Zhao Wei
 *
 */

@Service("CategoryUtil")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CategoryUtil  extends CommService implements Constant{
	
	private static Logger logger = Logger.getLogger(CategoryUtil.class);

	
	/**
	 * 
	 * @description get Forum Category List
	 * @return
	 */
	public List<Map<String, Object>> getForumCategoryList(){
		String hql = "select " +
				"new map(c.uuid as uuid, c.pid as pid, c.en_name as en_name, " +
				"c.en_desc as en_desc, c.cn_name as cn_name, c.cn_desc as cn_desc) " +
				"from "+ CATEGORY_ENTITY_NAME + " c " +
				"where c.is_delete='N' order by c.order_num";
		logger.debug(hql);
		List<?> categoryList = getDb().findByHql(hql, false);
		
		if(categoryList!=null){
			return listToTree(categoryList);
		}
		return null;
	}
	
	/**
	 * 
	 * @description save Or Update Category
	 * @author 
	 * @param entity
	 * @return
	 */
	public String saveOrUpdateCategory(Map<String, Object> entity){
		String uuid = (String)entity.get("uuid");
		Date currentDate = new Date();
		DBSupport dbSupport = getDb();
		if(uuid == null || uuid.length() == 0){
			entity.put("create_date", currentDate);
			entity.put("is_delete", 'N');
			String hqlMaxOrderNum = "select max(c.order_num) from " + CATEGORY_ENTITY_NAME + " c";
			List<?> maxOrderList = getDb().findByHql(hqlMaxOrderNum, false);
			Long newOrder = Long.parseLong(String.valueOf(maxOrderList.get(0)));
			entity.put("order_num", newOrder + 1);
			entity.put("last_modify_date", currentDate);
			return dbSupport.save(CATEGORY_ENTITY_NAME, entity);
		}else{
			String hql = "update " + CATEGORY_ENTITY_NAME +
				" set en_name=:en_name" +
				",cn_name=:cn_name" +
				",en_desc=:en_desc" +
				",cn_desc=:cn_desc" +
				",last_modifior_uuid=:last_modifior_uuid" +
				",last_modify_date=:last_modify_date " +
				" where uuid=:uuid";
			Query query = dbSupport.getDynamicSession().createQuery(hql);

			query.setString("uuid", uuid);
			query.setString("en_name", (String)entity.get("en_name"));
			query.setString("cn_name", (String)entity.get("cn_name"));
			query.setString("en_desc", (String)entity.get("en_desc"));
			query.setString("cn_desc", (String)entity.get("cn_desc"));
			query.setString("last_modifior_uuid", (String)entity.get("last_modifior_uuid"));
			query.setDate("last_modify_date", currentDate);
			
			query.executeUpdate();
			return uuid;
		}
	}
	
	/**
	 * 
	 * @description save Order
	 * @author 
	 * @param orderList
	 */
	public void saveOrder(List orderList){
		if(orderList == null) return;
		List itemList = treeToList(orderList, null);
		String hql = "update " + CATEGORY_ENTITY_NAME + " c " +
				"set c.order_num=:order_num where uuid=:uuid";
		Query query = getDb().getDynamicSession().createQuery(hql);
		for(int i = 0; i < itemList.size(); i++){
			Map item = (Map)itemList.get(i);
			query.setLong("order_num", i);
			query.setString("uuid", (String)item.get("uuid"));
			query.executeUpdate();
		}
	}
	
	/**
	 * 
	 * @description get Self And Parent Name
	 * @author 
	 * @param thisUuid
	 * @return
	 */
	public Map getSelfAndParentName(String thisUuid){
		Map result = new HashMap();
		if(thisUuid != null){
			Session session = getDb().getDynamicSession();
			String hql = "select new map(c.uuid as uuid, c.pid as pid," +
				"c.en_name as en_name, c.cn_name as cn_name) from " +
				CATEGORY_ENTITY_NAME + " c where c.uuid = :uuid";
			Query query = session.createQuery(hql);
			query.setString("uuid", thisUuid);
			Map self = (Map) query.uniqueResult();
			result.put("self", self);
			
			String pid = (String)self.get("pid");
			query.setString("uuid", pid);
			Map parent = (Map) query.uniqueResult();
			result.put("parent", parent);
		}
		return result;
	}
	
	/**
	 * 
	 * @description delete Category
	 * @author 
	 * @param uuid
	 * @param userUuid
	 */
	public void deleteCategory(String uuid, String userUuid){
		DBSupport dbSupport = getDb();
		String hql = "update " + CATEGORY_ENTITY_NAME + 
			" set last_modifior_uuid=:last_modifior_uuid, " +
			"is_delete=:is_delete, " +
			"last_modify_date=:last_modify_date " +
			"where uuid=:uuid";
		Query query = dbSupport.getDynamicSession().createQuery(hql);
		
		query.setString("uuid", uuid);
		query.setString("last_modifior_uuid", userUuid);
		query.setString("is_delete", "Y");
		query.setDate("last_modify_date", new Date());
		
		query.executeUpdate();
	}
	
	//list To Tree
	private List<Map<String, Object>> listToTree(List source){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map assistMap = new HashMap();
		for(int i = 0; i < source.size(); i++){
			Map item = (Map)source.get(i);
			String uuid = (String)item.get("uuid");
			logger.debug("uuid:" + uuid); 
			if(uuid == null){
				logger.debug("discard this item!");
				continue;
			}
			String pid = (String)item.get("pid");
			logger.debug("pid:" + pid);
			
			if(!assistMap.containsKey(uuid)){
				//never occur
				assistMap.put(uuid, item);
			}else{
				// exist
				Map tempObj = (Map)assistMap.get(uuid);
				item.put(CHILDREN_FIELD_NAME, tempObj.get(CHILDREN_FIELD_NAME));
				assistMap.put(uuid, item);
			}
				
			if(pid == null || pid.trim().length() == 0){
				//root node
				result.add(item);
			}else if(assistMap.containsKey(pid)){
				//have parent node
				Map pObj = (Map)assistMap.get(pid);
				
				if(pObj.containsKey(CHILDREN_FIELD_NAME)){
					//init children nodes
					List children = (List)pObj.get(CHILDREN_FIELD_NAME);
					children.add(item);
				}else{
					//not init children nodes
					List children = new ArrayList();
					children.add(item);
					pObj.put(CHILDREN_FIELD_NAME, children);
				}
			}else{
				//not exist parent
				Map pObj = new HashMap();
				pObj.put("uuid", pid);
				List children = new ArrayList();
				children.add(item);
				pObj.put(CHILDREN_FIELD_NAME, children);
				assistMap.put(pid, pObj);
			}
		}
		return result;
	}
	
	//tree To List
	private List treeToList(List orderList, List result){
		if(result == null) result = new ArrayList();
		if(orderList != null){
			for(int i = 0; i < orderList.size(); i++){
				Map item = (Map)orderList.get(i);
				Object uuid = item.get("uuid");
				if(uuid != null){
					result.add(item);
				}
				List children = (List)item.get(CHILDREN_FIELD_NAME);
				if(children != null){
					treeToList(children, result);
				}
			}
		}
		return result;
	}

}
