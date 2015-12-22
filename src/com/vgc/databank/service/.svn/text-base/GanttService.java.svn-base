package com.vgc.databank.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;
import org.utmost.util.DateUtil;

import com.vgc.databank.util.Constant;
import com.vgc.databank.util.FuncUtil;

/**
 * 
 *
 * ClassName: GanttService 
 * Function: TODO Gantt business class
 *
 * @author wangweimin
 * @Date 2009 Nov 18, 2009 11:44:55 AM
 */
@Service("GanttService")
@SuppressWarnings("unchecked")
public class GanttService implements Constant {
    
    @Autowired
    private AutoService autoService;
    
    @Autowired
	public FuncUtil funcUtil;
    
    private final static String SELECTED_COLUMN_STRING = "uuid,no_,standard_attribute1,standard_attribute2,standard_attribute3,version_,"+
                                                  "title_en,title_ch,summary,summary_swf,func_uuid,author,last_modify_date_str,"+
                                                  "importance,last_modify_date_str,status,execute_date_str,online_auto_execute_date_str,"+
                                                  "|||,uuid,recordid,commondata_uuid,start_date,end_date,information,remark,phase,state";
    /**
     * construct Select column
     * 
     * @param 
	 * @return
     */
    private String constructSelectStr(){
      String[] strArr = SELECTED_COLUMN_STRING.split(",");
      StringBuffer sb = new StringBuffer(); 
      String preStr = "bc";
      for(int i=0;i<strArr.length;i++){
          if("|||".equals(strArr[i])){
            preStr = "bg";
            continue;
          }
          if(i>0 && strArr[i-1].equals("|||")){
              sb.append(preStr+"."+strArr[i] +" as gantt"+strArr[i]+",");
          }else{
              sb.append(preStr+"."+strArr[i] +" as "+strArr[i]+",");
          }
      }
      String returnStr = sb.toString();
      return returnStr.substring(0,returnStr.length()-1);
    }
    
    /**
     * queryGanttsByFuncs: Query all gantt recorder based on leaf node array
     *
     * @param funcuuids Leaf node uuid array
     * @param isHistory if is history
     * @return List<HashMap> GANTT recorder list
     * @throws 
    */
    public List<HashMap> queryGanttsByFuncs(String[] funcuuids, boolean isHistory){
        StringBuffer hql = new StringBuffer();
        hql.append("select new map( ");
        hql.append(constructSelectStr());
        hql.append(") ");
        hql.append("from ");
        hql.append("B_COMMONDATA bc, ");
        hql.append("B_GANTT bg ");
        hql.append("where bc.uuid=bg.commondata_uuid ");
        hql.append(isHistory ?" and bc.state='1' ":" and bc.state='0' ");   //history recorder
        hql.append("and bc.func_uuid in ( ");
        for(int i=0; i<funcuuids.length; i++){
            if(i == (funcuuids.length-1)){
                hql.append("'"+funcuuids[i]+"'");
            }else{
                hql.append("'"+funcuuids[i]+"',");
            }
        }
        hql.append(") ");
        hql.append("  order by COALESCE(bc.standard_attribute1, '') ,COALESCE(bc.standard_attribute2, '') ,COALESCE(bc.standard_attribute3, '')  ,bc.no_ ,bc.version_   ");
        
        //get all gantt recorder
        List<HashMap> tempGantts = autoService.findByHql(hql.toString());
        List<HashMap> gantts = new ArrayList<HashMap>();
        
        //Piece together data(put gantt table recorder list into commondata)
        for(int i=0; i<tempGantts.size(); i++){
            HashMap temp = tempGantts.get(i);
            boolean flag = true;
            for(int j=0; j<gantts.size(); j++){
                if(temp.get("uuid").equals(gantts.get(j).get("uuid"))){
                    
                    //Piece together gantt info(is a map)
                    HashMap<String, Object> gi = new HashMap<String, Object>();
                    gi.put("uuid", temp.get("ganttuuid"));
                    gi.put("recordid", temp.get("recordid"));
                    gi.put("commondata_uuid", temp.get("commondata_uuid"));
                    Long sd = (Long) temp.get("start_date");  //start date 
                    Long ed = (Long) temp.get("end_date");	//end date
                    gi.put("start_date", new Date(sd));
                    gi.put("end_date", new Date(ed));
                    gi.put("phase", temp.get("phase"));
                    gi.put("information", temp.get("information"));
                    gi.put("remark", temp.get("remark"));
                    gi.put("state", temp.get("state"));
                    gi.put("type", temp.get("date"));   
                    
                    List<HashMap> ganttsInfo = (List<HashMap>) gantts.get(j).get("gantts");
                    
                    if(ganttsInfo==null){
                        ganttsInfo = new ArrayList<HashMap>();
                    }
                    
                    ganttsInfo.add(gi);
                    gantts.get(j).put("gantts", ganttsInfo);
                    flag = false;
                    break;
                }
            }
            
            if(flag){
                //Piece gantt info(map)
                HashMap<String, Object> gi = new HashMap<String, Object>();
                gi.put("uuid", temp.get("ganttuuid"));
                gi.put("recordid", temp.get("recordid"));
                gi.put("commondata_uuid", temp.get("commondata_uuid"));
                Long sd = (Long) temp.get("start_date");	//start date
                Long ed = (Long) temp.get("end_date");	//end date
                gi.put("start_date", new Date(sd));
                gi.put("end_date", new Date(ed));
                gi.put("phase", temp.get("phase"));
                gi.put("state", temp.get("state"));
                gi.put("type", temp.get("date"));   
                
                List<HashMap> ganttsInfo = new ArrayList<HashMap>();
                ganttsInfo.add(gi);
                
                temp.put("gantts", ganttsInfo);
                
                gantts.add(temp);
            }
        }
        return gantts;
    }
    
    /**
     * queryGanttsByUuid: Query gantt recorder based on recorder primary key
     *
     * @param funcuuids Leaf node uuid array
     * @param isHistory: if is history
     * @return List<HashMap> GANTT recorder list
     * @throws 
    */
    public List<HashMap> queryGanttsByUuid(String uuid){
        StringBuffer hql = new StringBuffer();
        hql.append("select new map( ");
        hql.append(constructSelectStr());
        hql.append(") ");
        hql.append("from ");
        hql.append("B_COMMONDATA bc, ");
        hql.append("B_GANTT bg ");
        hql.append("where bc.uuid=bg.commondata_uuid ");
        hql.append("and bc.uuid='" + uuid + "' ");
        //get all gantt recorder
        List<HashMap> tempGantts = autoService.findByHql(hql.toString());
        
        List<HashMap> gantts = new ArrayList<HashMap>();
        
        //Piece together data(put gantt recorder list into commondata)
        for(int i=0; i<tempGantts.size(); i++){
            HashMap temp = tempGantts.get(i);
            boolean flag = true;
            for(int j=0; j<gantts.size(); j++){
                if(temp.get("uuid").equals(gantts.get(j).get("uuid"))){
                    //Piece together gantt info(is a map)
                    HashMap<String, Object> gi = new HashMap<String, Object>();
                    gi.put("uuid", temp.get("ganttuuid"));
                    gi.put("recordid", temp.get("recordid"));
                    gi.put("commondata_uuid", temp.get("commondata_uuid"));
                    Long sd = (Long) temp.get("start_date");  //start date
                    Long ed = (Long) temp.get("end_date");	//end date
                    gi.put("start_date", new Date(sd));
                    gi.put("end_date", new Date(ed));
                    gi.put("phase", temp.get("phase"));
                    gi.put("information", temp.get("information"));
                    gi.put("remark", temp.get("remark"));
                    gi.put("state", temp.get("state"));
                    gi.put("type", temp.get("date"));   
                    
                    List<HashMap> ganttsInfo = (List<HashMap>) gantts.get(j).get("gantts");
                    
                    if(ganttsInfo==null){
                        ganttsInfo = new ArrayList<HashMap>();
                    }
                    ganttsInfo.add(gi);
                    gantts.get(j).put("gantts", ganttsInfo);
                    flag = false;
                    break;
                }
            }
            if(flag){
                //Piece together gantt info(map)
                HashMap<String, Object> gi = new HashMap<String, Object>();
                gi.put("uuid", temp.get("ganttuuid"));
                gi.put("recordid", temp.get("recordid"));
                gi.put("commondata_uuid", temp.get("commondata_uuid"));
                Long sd = (Long) temp.get("start_date");	//start date
                Long ed = (Long) temp.get("end_date");	//end date
                gi.put("start_date", new Date(sd));
                gi.put("end_date", new Date(ed));
                gi.put("phase", temp.get("phase"));
                gi.put("state", temp.get("state"));
                gi.put("type", temp.get("date"));   
                
                List<HashMap> ganttsInfo = new ArrayList<HashMap>();
                ganttsInfo.add(gi);
                temp.put("gantts", ganttsInfo);
                gantts.add(temp);
            }
        }
        List<String> keyStr = new ArrayList<String>();      //all is need to modify string date format
        keyStr.add("issuance_date_str");
        keyStr.add("execute_date_str");
        keyStr.add("last_modify_date_str");
        keyStr.add("pre_execute_date_str");
        keyStr.add("online_auto_execute_date_str");
        keyStr.add("publication_date_str");
        DateUtil.transferStrDate(gantts,keyStr);
        return gantts;
    }
    
    /**
     * queryGanttsByFunc:  Query all gantt plan based on user and path
     *
     * @param user_uuid user uuid
     * @param func_uuid node uuid
     * @param isLeaf If is a leaf node 
     * @param isHistory If look history
     * @return List<HashMap>
     * @throws 
    */
    public List<HashMap> queryGanttsByFunc(String user_uuid, String func_uuid, boolean isLeaf, boolean isHistory){
        String[] funcuuids = null;
        if(!isLeaf){
            List<HashMap> func = funcUtil.queryChildFuncsByFuncId(user_uuid, func_uuid);
            if(func==null || func.size()==0){
                return null;
            }
            funcuuids = new String[func.size()];
            
            for(int i=0; i<func.size(); i++){
                funcuuids[i] = func.get(i).get("funcuuid").toString();
            }
        }else{
            funcuuids = new String[1];
            funcuuids[0] = func_uuid;
        }
        List<HashMap> gantts = queryGanttsByFuncs(funcuuids, isHistory);
        for(int i=0; i<gantts.size(); i++){
            HashMap gantt = gantts.get(i);
            List<HashMap> gaList = queryGanttAppendix(gantt.get("commondata_uuid").toString());
            gantt.put("appendixs", gaList);
        }
        List<String> keyStr = new ArrayList<String>();      //all is need to modify string date format
        keyStr.add("issuance_date_str");
        keyStr.add("execute_date_str");
        keyStr.add("last_modify_date_str");
        keyStr.add("pre_execute_date_str");
        keyStr.add("online_auto_execute_date_str");
        keyStr.add("publication_date_str");
        DateUtil.transferStrDate(gantts,keyStr);
        return gantts;
    }
    
    /**
     * queryGanttAppendix: Query one recorder all attachment based on commondata_uuid
     *
     * @param commondata_uuid recorder table uuid
     * @return List<HashMap>
     * @throws 
    */
    public List<HashMap> queryGanttAppendix(String commondata_uuid){
        StringBuffer hql = new StringBuffer();
        hql.append("from B_GANTT_APPENDIX where commondata_uuid='"+commondata_uuid+"'");
        List<HashMap> gaList = autoService.findByHql(hql.toString());
        return gaList;
    }
    
    /**
     * Query gantt appendix by date.
     * 
     * @param commondata_uuid
     * @param year
     * @param mouth
     * @return
     */
    public List<HashMap> queryGanttAppendixByDate(String commondata_uuid, String year, String mouth){
        DateFormat format = new SimpleDateFormat("yyyy/MM");
        Date sd = null; //first day month
        Date ed = null; //next month first day
        try {
            sd = format.parse(year+"/"+mouth);
            if(!mouth.equals("12")){
                ed = format.parse(year+"/"+(Integer.parseInt(mouth)+1));
            }else{
                ed = format.parse((Integer.parseInt(year)+1)+"/1");
            }
        } catch (ParseException e) {
            
            e.printStackTrace();
            
        }
        long sdTime = new Date().getTime(); //get system time
        if(sd!=null){
        	sdTime = sd.getTime();  
        }
        long edTime = new Date().getTime(); //get system time
        if(ed!=null){
        	edTime = ed.getTime();
        }
        
        StringBuffer hql = new StringBuffer();
        hql.append(" from B_GANTT_APPENDIX where commondata_uuid='"+commondata_uuid+"' ");
        hql.append(" and current_date >= " + sdTime);
        hql.append(" and current_date < " + edTime);
        List<HashMap> gaList = autoService.findByHql(hql.toString());
        return gaList;
    }
    /**
     * saveGantts: save gantt table data
     *
     * @param gantts
     * @return long
     * @throws 
    */
    public long saveGantt(HashMap gantt){
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");  //transfer date to define format string date  
        String sd = DateUtil.transferDateStrFormat(gantt.get("start_date").toString(),format);
        String ed = DateUtil.transferDateStrFormat(gantt.get("end_date").toString(),format);
        String stimeStr = "";
        String etimeStr = "";
        try {
            if(sd!=null && !"".equals(sd))
                stimeStr = format.parse(sd).getTime()+"";
            if(ed!=null && !"".equals(ed))
                etimeStr = format.parse(ed).getTime()+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        gantt.put("start_date", stimeStr);
        gantt.put("end_date", etimeStr);
        
        autoService.saveOrUpdate("B_GANTT", gantt);
        
        return OK;
    }
    
    /**
     * Add gantt appendix
     * @param ga
     * @return
     */
    public long addGanttAppendix(HashMap ga){
        try {
            DateFormat format = new SimpleDateFormat("yyyy/MM");
            
            ga.put("current_date", format.parse(ga.get("current_date").toString()).getTime());
            
            autoService.save("B_GANTT_APPENDIX", ga);
        } catch (Exception e) {
            e.printStackTrace();
            return SERVER_ERROR;
        }
        
        return OK;
    }
    
    /**
     * publish:publish plan
     *
     * @param commondatauuid    information uuid
     * @return long
     * @throws 
    */
    public long publish(String commondatauuid){
        try {
            HashMap commondata = (HashMap) autoService.findByUUID("B_COMMONDATA", commondatauuid);
            HashMap func = (HashMap) autoService.findByUUID("U_PORTAL_FUNC", commondata.get("func_uuid").toString());
            String hql = "from U_PORTAL_FUNC u where u.classuuid = '02' " +
                    "and u.funcname = '"+func.get("funcname")+"'";
            List<HashMap> node = autoService.findByHql(hql);
            if(node.size()>0){
                commondata.put("state", "1");
                autoService.update("B_COMMONDATA", commondata); //update not publish of information state to 1(become history)
                
                commondata.remove("uuid");
                commondata.put("classuuid", "02");
                commondata.put("func_uuid", node.get(0).get("uuid"));
                autoService.save("B_COMMONDATA", commondata);   //Add a same info under issued info(under node)
            }else{
                return 404L;    //Can't find corresponding node in issued class
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return SERVER_ERROR;
        }
        return OK;
    }
    
    /**
     * republish: publish rollback(change publish to no publish)
     *
     * @param commondatauuid    information uuid
     * @return long
     * @throws 
    */
    public long republish(String commondatauuid){
        try {
            HashMap commondata = (HashMap) autoService.findByUUID("B_COMMONDATA", commondatauuid);
            commondata.put("state", "0");   //change publish info to no publish
            autoService.update("B_COMMONDATA", commondata); 
        } catch (Exception e) {
            e.printStackTrace();
            return SERVER_ERROR;
        }
        return OK;
    }
    
    /**
     * deleteFile:delete attachment
     *
     * @param fileuuid  attachment table uuid
     * @param filename  attachment name
     * @return long
     * @throws 
    */
    public long deleteFile(String fileuuid ,String filename){
        try {
            autoService.deleteByUUID("B_GANTT_APPENDIX", fileuuid);
            //delete attachment
        }catch(Exception e){
            e.printStackTrace();
            return SERVER_ERROR;
        }
        return OK;
    }
}