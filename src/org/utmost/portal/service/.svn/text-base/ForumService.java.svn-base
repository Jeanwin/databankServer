/**
 * 
 */
package org.utmost.portal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;
import org.utmost.common.DBSupport;
import org.utmost.util.CategoryUtil;

import com.vgc.databank.service.RightService;
import com.vgc.databank.util.Constant;

/**
 * 
 * @description Forum Service
 * @author 
 * @version 3.2.0
 * @date Jan 29, 2015 12:07:19 PM
 */
@Service("ForumService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ForumService extends CommService implements Constant{
	private static Logger logger = Logger.getLogger(ForumService.class);

	@Autowired
	private CategoryUtil categoryUtil;
	
	/**
	 * 
	 * @description get Forum Category List With Statistic
	 * @author 
	 * @return
	 */
	public List<Map<String, Object>> getForumCategoryListWithStatistic(){
		List<Map<String, Object>> bigList = null;
		List<Map<String, Object>> categoryList = categoryUtil.getForumCategoryList();
		if(categoryList.size() != 0){
			bigList = (List)categoryList.get(0).get(CHILDREN_FIELD_NAME);
		}
		
		if(bigList == null)
			bigList = new ArrayList<Map<String,Object>>();
		
		Map userMap = new HashMap();
		for(int i = 0; i < bigList.size(); i++){
			Map item = bigList.get(i);
			List smallList = (List)item.get(CHILDREN_FIELD_NAME);
			if(smallList != null){
				for(int j = 0; j < smallList.size(); j++){
					Map smallItem = (Map)smallList.get(j);
					String smallUuid = (String)smallItem.get("uuid");
					
					Integer topicCount = getTopicCountBySmallUuid(smallUuid);
					smallItem.put("topicCount", topicCount);
					
					Integer replyCount = getReplyCountBySmallUuid(smallUuid);
					smallItem.put("replyCount", replyCount);
					
					if(topicCount != 0){
						Map lastPost = getLastPostBySmallUuid(smallUuid);
						
						String lastPostDate = formatDate(lastPost.get("create_date"));
						smallItem.put("lastPostDate", lastPostDate);
						
						String lastPostUserUuid = (String)lastPost.get("author_uuid");
						String lastPostUsername = getUserNameByUuid(userMap, lastPostUserUuid);
						smallItem.put("lastPostUsername", lastPostUsername);
					}else{
						smallItem.put("lastPostDate", "");
						smallItem.put("lastPostUsername", "");
					}
				}
			}
		}
		return bigList;
	}
	
	/**
	 * 
	 * @description get Topic List
	 * @author 
	 * @param categoryUuid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map getTopicList(String categoryUuid, Integer pageNo, Integer pageSize){
		Map result = new HashMap();
		
		DBSupport dbSupport = getDb();
		Session session = dbSupport.getDynamicSession();
		String hqlCondition = "from " + TOPIC_ENTITY_NAME + " t where t.pid=:pid and t.is_delete='N'";
		String hql = hqlCondition + " order by t.create_date desc";
		Query queryCount = session.createQuery("select count(t.uuid) " + hqlCondition);
		queryCount.setString("pid", categoryUuid);
		Long rowCount = (Long)queryCount.uniqueResult();
		//return records
		result.put("rowCount", rowCount);
		
		Query query = session.createQuery(hql);
		query.setString("pid", categoryUuid);
		Map pagination = pagination(query, pageNo, pageSize, rowCount);
		result.put("pageNo", pagination.get("pageNo"));
		result.put("pageSize", pagination.get("pageSize"));
		
		Integer topicCount = getTopicCountBySmallUuid(categoryUuid);
		result.put("topicCount", topicCount);
		
		Integer replyCount = getReplyCountBySmallUuid(categoryUuid);
		result.put("replyCount", replyCount);
		
		List list = query.list();
		
		result.put("topicList", constructList(list));
		
		return result;
	}
	/**
	 * 
	 * @description find Topic List By Kewords
	 * @author
	 * @param keywords
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map findTopicListByKewords(String keywords, Integer pageNo, Integer pageSize){
		Map result = new HashMap();
		
		if(keywords != null && keywords.length() != 0){
			keywords = "%" + keywords + "%";
		}
		DBSupport dbSupport = getDb();
		Session session = dbSupport.getDynamicSession();
		String hqlCondition = "from " + TOPIC_ENTITY_NAME + " t where (t.title like :keywords or t.content like :keywords) and t.is_delete='N'";
		String hql = hqlCondition + " order by t.create_date desc";
		Query queryCount = session.createQuery("select count(t.uuid) " + hqlCondition);
		queryCount.setString("keywords", keywords);
		Long rowCount = (Long)queryCount.uniqueResult();
		//return records
		result.put("rowCount", rowCount);
		
		Query query = session.createQuery(hql);
		query.setString("keywords", keywords);
		Map pagination = pagination(query, pageNo, pageSize, rowCount);
		result.put("pageNo", pagination.get("pageNo"));
		result.put("pageSize", pagination.get("pageSize"));
		List list = query.list();
		result.put("topicList", constructList(list));
		
		return result;
	}
	//construct List
	private List constructList(List list){
  	    Map userMap = new HashMap();
        List topicList = new ArrayList();
        for(int i = 0; i < list.size(); i++){
            Map item = (Map)list.get(i);
            Map topicItem = new HashMap();
            String topicUuid = (String)item.get("uuid");
            topicItem.put("uuid", item.get("uuid"));
            topicItem.put("title", item.get("title"));
            topicItem.put("content", item.get("content"));
            
            String createDate = formatDate(item.get("create_date"));
            topicItem.put("create_date", createDate);
            
            String username = getUserNameByUuid(userMap, (String)item.get("author_uuid"));
            topicItem.put("username", username);
            
            topicItem.put("postsCount", getReplyCountByTopicUuid(topicUuid) + 1);
            
            Map lastPost = getLastReplyByTopicUuid(topicUuid);
            if(lastPost == null){
                topicItem.put("lastPostUsername", username);
                topicItem.put("lastPostDate", createDate);
            }else{
                String lastPostDate = formatDate(lastPost.get("create_date"));
                topicItem.put("lastPostDate", lastPostDate);
                
                String lastPostUsername = getUserNameByUuid(userMap, 
                        (String)lastPost.get("author_uuid"));
                topicItem.put("lastPostUsername", lastPostUsername);
            }
            topicList.add(topicItem);
        }
        return topicList;
	}
	
	/**
	 * 
	 * @description get Topic Detail By Topic Uuid
	 * @author 
	 * @param topicUuid
	 * @return
	 */
	public Map getTopicDetailByTopicUuid(String topicUuid){
		Map result = new HashMap();
		
		Map topicInfo = getTopicInfoByTopicUuid(topicUuid);
		result.put("topicInfo", topicInfo);
		
		String smallUuid = (String)topicInfo.get("pid");
		Map categoryMap = categoryUtil.getSelfAndParentName(smallUuid);
		result.put("categoryMap", categoryMap);
		
		List replyList = getReplyListByTopicUuid(topicUuid);
		result.put("replyList", replyList);
		
		return result;
	}
	
	/**
	 * 
	 * @description get Reply List By Topic Uuid
	 * @author 
	 * @param topicUuid
	 * @return
	 */
	public List getReplyListByTopicUuid(String topicUuid){
		List result = null;
		
		if(topicUuid != null){
			Session session = getDb().getDynamicSession();
			String hql = "select new map(r.uuid as uuid, r.content as content," +
					"r.author_uuid as author_uuid, r.create_date as create_date) " +
					"from " + REPLY_ENTITY_NAME + " r " +
					"where r.is_delete = 'N' and r.pid=:pid " +
					"order by r.create_date";
			Query query = session.createQuery(hql);
			query.setString("pid", topicUuid);
			result = query.list();
			Map userMap = new HashMap();
			if(result != null){
				for(int i = 0; i < result.size(); i++){
					Map item = (Map)result.get(i);
					
					item.put("create_date", formatDate(item.get("create_date")));
					
					String authorUuid = (String)item.get("author_uuid");
					Map userEntity = getUserEntityByUuid(userMap, authorUuid);
					String username = (String) userEntity.get("username");
					item.put("username", username);
					
					String email = (String)userEntity.get("email");
					item.put("email", email);
					
					Integer topicCount = getTopicCountByAuthorUuid(authorUuid);
					item.put("topicCount", topicCount);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @description save Or Update Topic
	 * @param topic
	 * @param userUuid
	 * @return
	 */
	public String saveOrUpdateTopic(Map topic, String userUuid){
		String topicUuid = (String)topic.get("uuid");
		Date currentDate = new Date();
		if(topicUuid == null){
			topic.put("author_uuid", userUuid);
			topic.put("create_date", currentDate);
			topic.put("last_modify_date", currentDate);
			topic.put("last_modifior_uuid", userUuid);
			topic.put("is_delete", "N");
			return getDb().save(TOPIC_ENTITY_NAME, topic);
		}else{
			Map topic0 = getEntityByUuid(TOPIC_ENTITY_NAME, topicUuid);
			if(SUPERMAN_UUID.equals(userUuid) || userUuid.equals(topic0.get("author_uuid"))){
				topic0.put("title", topic.get("title"));
				topic0.put("content", topic.get("content"));
				topic0.put("last_modify_date", currentDate);
				topic0.put("last_modifior_uuid", userUuid);
				getDb().update(TOPIC_ENTITY_NAME, topic0);
			}
			return topicUuid;
		}
	}
	/**
	 * 
	 * @description get Display Topic
	 * @param topicUuid
	 * @return
	 */
	public Map getDisplayTopic(String topicUuid){
		Map topic = getEntityByUuid(TOPIC_ENTITY_NAME, topicUuid);
		Map result = new HashMap();
		
		result.put("title", topic.get("title"));
		result.put("content", topic.get("content"));
		
		return result;
	}
	
	/**
	 * 
	 * @description delete Topic
	 * @param topicUuid
	 * @param userUuid
	 */
	public void deleteTopic(String topicUuid, String userUuid){
		Map topic = getEntityByUuid(TOPIC_ENTITY_NAME, topicUuid);
		if(SUPERMAN_UUID.equals(userUuid) || userUuid.equals(topic.get("author_uuid"))){
			topic.put("is_delete", "Y");
			topic.put("last_modify_date", new Date());
			topic.put("last_modifior_uuid", userUuid);
			getDb().update(TOPIC_ENTITY_NAME, topic);
		}
	}
	
	/**
	 * 
	 * @description save Or Update Reply
	 * @param reply
	 * @param userUuid
	 * @return
	 */
	public String saveOrUpdateReply(Map reply, String userUuid){
		String replyUuid = (String)reply.get("uuid");
		Date currentDate = new Date();
		if(replyUuid == null){
			reply.put("author_uuid", userUuid);
			reply.put("create_date", currentDate);
			reply.put("last_modify_date", currentDate);
			reply.put("last_modifior_uuid", userUuid);
			reply.put("is_delete", "N");
			return getDb().save(REPLY_ENTITY_NAME, reply);
		}else{
			Map topic0 = getEntityByUuid(REPLY_ENTITY_NAME, replyUuid);
			if(SUPERMAN_UUID.equals(userUuid) || userUuid.equals(topic0.get("author_uuid"))){
				topic0.put("content", reply.get("content"));
				topic0.put("last_modify_date", currentDate);
				topic0.put("last_modifior_uuid", userUuid);
				getDb().update(REPLY_ENTITY_NAME, topic0);
			}
			return replyUuid;
		}
	}
	/**
	 * 
	 * @description get Display Reply
	 * @param replyUuid
	 * @return
	 */
	public Map getDisplayReply(String replyUuid){
		Map reply = getEntityByUuid(REPLY_ENTITY_NAME, replyUuid);
		Map result = new HashMap();
		
		result.put("content", reply.get("content"));
		
		return result;
	}
	
	/**
	 * 
	 * @description delete Reply
	 * @param replyUuid
	 * @param userUuid
	 */
	public void deleteReply(String replyUuid, String userUuid){
		Map reply = getEntityByUuid(REPLY_ENTITY_NAME, replyUuid);
		if(SUPERMAN_UUID.equals(userUuid) || userUuid.equals(reply.get("author_uuid"))){
			reply.put("is_delete", "Y");
			reply.put("last_modify_date", new Date());
			reply.put("last_modifior_uuid", userUuid);
			getDb().update(REPLY_ENTITY_NAME, reply);
		}
	}
	
	
	/**
	 * 
	 * @description  get Hql Fields From Entity
     * eg:<br />
     * 1.hqlObjAlias is "c",appendAliasAfterField is false,result： c.field1 as field1, c.field2 as field2  <br />
     * 2.hqlObjAlias is "c",appendAliasAfterField is true,result： c.field1 as field1c, c.field2 as field2c <br />
     * 
	 * @author
	 * @param entityName
	 * @param hqlObjAlias
	 * @param appendAliasAfterField
	 * @return
	 */
	protected String getHqlFieldsFromEntity(String entityName, String hqlObjAlias, Boolean appendAliasAfterField){
		if(appendAliasAfterField == null) appendAliasAfterField = false;
		StringBuilder result = new StringBuilder();
		ClassMetadata classMetadata = getDb().getSessionFactory().getClassMetadata(entityName);
		String[] propertyNames = classMetadata.getPropertyNames();
		for(String property:propertyNames){
			if(appendAliasAfterField)
				result.append(hqlObjAlias + "." + property + " as " + property + hqlObjAlias);
			else
				result.append(hqlObjAlias + "." + property + " as " + property);
				
			result.append(",");
		}
		if(result.length() > 0){
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}
	
	
	
	//get User Entity By Uuid
	private Map getUserEntityByUuid(Map userMap, String uuid){
		Session session = getDb().getDynamicSession();
		Object userEntity = userMap.get(uuid);
		if(userEntity != null){
			return (Map)userEntity;
		}else{
			String hql = "from " + USER_ENTITY_NAME + " u " +
					"where (u.uuid=:uuid) or " +
					"('" + SUPERMAN_UUID + "'=:uuid and lower(u.loginname) = lower('" + RightService.SUPERMAN_LOGINNAME + "'))";
			Query query = session.createQuery(hql);
			query.setString("uuid", uuid);
			List list = query.list();
			if(list == null || list.size() == 0){
				return null;
			}else{
				return (Map)list.get(0);
			}
		}
	}
	
	//get User Name By Uuid
	private String getUserNameByUuid(Map userMap, String uuid){
		Map user = getUserEntityByUuid(userMap, uuid);
		if(user == null)
			return "";
		else{
			return (String)user.get("username");
		}
	}
	
	//get Last Reply By Topic Uuid
	private Map getLastReplyByTopicUuid(String topicUuid){
		Session session = getDb().getDynamicSession();
		String hql = "from " + REPLY_ENTITY_NAME + 
			" r where r.pid=:pid and r.is_delete='N' order by r.create_date desc";
		Query query = session.createQuery(hql);
		query.setString("pid", topicUuid);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List list = query.list();
		if(list == null || list.size() == 0){
			return null;
		}else{
			return (Map)list.get(0);
		}
	}
	
	//get Entity By Uuid
	private Map getEntityByUuid(String entityName, String uuid){
		Session session = getDb().getDynamicSession();
		String hql = "from " + entityName + " t " +
			"where t.uuid=:uuid";
		Query query = session.createQuery(hql);
		query.setString("uuid", uuid);
		return (Map)query.uniqueResult();
	}
	
	/**
	 * 
	 * @description get Topic Info By Topic Uuid
	 * @author 
	 * @param topicUuid
	 * @return
	 */
	protected Map getTopicInfoByTopicUuid(String topicUuid){
		Map result = new HashMap();
		Map topic = getEntityByUuid(TOPIC_ENTITY_NAME, topicUuid);
		
		result.put("uuid", topic.get("uuid"));
		result.put("pid", topic.get("pid"));
		result.put("title", topic.get("title"));
		result.put("content", topic.get("content"));
		result.put("author_uuid", topic.get("author_uuid"));
		
		String topicCreateDate = formatDate(topic.get("create_date"));
		result.put("create_date", topicCreateDate);
		
		Integer replyCount = getReplyCountByTopicUuid(topicUuid);
		result.put("replyCount", replyCount);
		
		String topicAuthorUuid = (String)topic.get("author_uuid");
		Map userEntity = getUserEntityByUuid(new HashMap(), topicAuthorUuid);
		result.put("username", userEntity.get("username"));
		result.put("email", userEntity.get("email"));
		
		Integer topicCount = getTopicCountByAuthorUuid(topicAuthorUuid);
		result.put("topicCount", topicCount);
		
		return result;
	}
	
	//get Topic Count By Small Uuid
	private Integer getTopicCountBySmallUuid(String smallUuid){
		Session session = getDb().getDynamicSession();
		String hql = "select count(t.uuid) from " + TOPIC_ENTITY_NAME + " t " +
				"where t.pid=:pid and t.is_delete='N'";
		Query query = session.createQuery(hql);
		query.setString("pid", smallUuid);
		Long result = (Long)query.uniqueResult();
		return result.intValue();
	}
	
	//get Topic Count By Author Uuid
	private Integer getTopicCountByAuthorUuid(String authorUuid){
		Session session = getDb().getDynamicSession();
		String hql = "select count(t.uuid) from " + TOPIC_ENTITY_NAME + " t " +
			"where t.author_uuid=:author_uuid and t.is_delete = 'N'";
		Query query = session.createQuery(hql);
		query.setString("author_uuid", authorUuid);
		Long result = (Long)query.uniqueResult();
		return result.intValue();
	}
	
	//get Last Post By Small Uuid
	private Map getLastPostBySmallUuid(String smallUuid){
		Session session = getDb().getDynamicSession();
		String topicHql = "select new map(t.uuid as uuid, t.author_uuid as author_uuid, " +
				"t.create_date as create_date) " +
				"from " + TOPIC_ENTITY_NAME + " t " +
				"where t.is_delete='N' and t.pid=:pid " +
				"order by t.create_date desc";
		Query query = session.createQuery(topicHql);
		query.setFirstResult(0);
		query.setMaxResults(1);
		query.setString("pid", smallUuid);
		List list = query.list();
		if(list == null || list.size() == 0){
			return new HashMap();
		}else{
			Map lastTopic = (Map)list.get(0);
			Date topicCreateDate = (Date)lastTopic.get("create_date");
			String replyHql = "select new Map(r.uuid as uuid, r.author_uuid as author_uuid, " + 
					"r.create_date as create_date) " +
					"from " + REPLY_ENTITY_NAME + " r, " + TOPIC_ENTITY_NAME + " t " +
					"where t.is_delete='N' and r.is_delete='N' and t.uuid = r.pid " +
					"and t.pid=:pid " +
					"order by r.create_date desc";
			Query query2 = session.createQuery(replyHql);
			query2.setString("pid", smallUuid);
			query2.setFirstResult(0);
			query2.setMaxResults(1);
			List list2 = query2.list();
			if(list2 == null || list2.size() == 0){
				return lastTopic;
			}else{
				Map lastReply = (Map)list2.get(0);
				Date replyCreateDate = (Date)(lastReply.get("create_date"));
				if(topicCreateDate.compareTo(replyCreateDate) > 0){
					return lastTopic;
				}else{
					return lastReply;
				}
			}
		}
	}
	
	//get Reply Count By Small Uuid
	private Integer getReplyCountBySmallUuid(String smallUuid){
		Session session = getDb().getDynamicSession();
		String hql = "select count(r.uuid) from " + REPLY_ENTITY_NAME + " r, " +
				TOPIC_ENTITY_NAME + " t " +
				"where t.uuid = r.pid and t.is_delete='N' and r.is_delete='N' " +
				"and t.pid=:pid";
		
		Query query = session.createQuery(hql);
		query.setString("pid", smallUuid);
		Long result = (Long)query.uniqueResult();
		return result.intValue();
	}
	
	//get Reply Count By Topic Uuid
	private Integer getReplyCountByTopicUuid(String topicUuid){
		Session session = getDb().getDynamicSession();

		String hql = "select count(r.uuid) from " + 
			REPLY_ENTITY_NAME + " r where r.pid=:pid and r.is_delete='N'";
		Query query = session.createQuery(hql);
		query.setString("pid", topicUuid);
		return ((Long)query.uniqueResult()).intValue();
	}
	
	//pagination
	private Map pagination(Query query, Integer pageNo, Integer pageSize, Long rowCount){
		Map result = new HashMap();
		if(pageSize == null || pageSize == 0) pageSize = DEFAULT_PAGE_SIZE;
		
		result.put("pageSize", pageSize);
		
		Integer pageCount = ((rowCount.intValue() - 1) / pageSize + 1); 
		
		if(pageNo < 1) pageNo = 1;
		if(pageNo > pageCount) pageNo = pageCount;

		
		result.put("pageNo", pageNo);
		
		int firstIndex = (pageNo - 1) * pageSize;
		query.setFirstResult(firstIndex);
		query.setMaxResults(pageSize);
		return result;
	}
}
