package com.vgc.databank.service;

import java.util.HashMap;

import flex.messaging.io.ArrayCollection;
/**
 * information publish interface
 * Author：Xueping Lu
 * Nov 10, 2009
*/
public interface InformationPublishInterface {
	/**
	 * judgeFunccode: judging Funccode if is standard
	 * Query FuncCode if is standard in table U_PORTAL_FUNC by uuid, 
	 * if is standard return true, or not return false
	 * @param uuid directory uuid
	 * @return Boolean success or not
	 * @throws 
	*/
	public Boolean judgeFuncCode(String uuid);

	/**
	* queryClassField: query table B_CLASS_FIELD 
	* query table B_CLASS_FIELD to obtain value of segment FIELD_NAME, FIELD_TYPE, FIELD_VALUE, 
	* 		TOOLTIP and REGEX, then save these segments to list
	* @param uuid directory uuid
	* @return List< HashMap >Obtain segment list
	* @throws 
	*/
	public ArrayCollection queryClassField (String uuid);
	
	/**
	* addCommonData: Add B_COMMONDATA 
	* pass commondata to method addB_COMMONDATA, 
	* then add data to table B_COMMONDATA
	* @param commondata  data list packaged
	* @return Boolean if add successfully return true, failure return false
	* @throws 
	*/
	public boolean addCommonData (ArrayCollection ac);

	/**
	 * 
	 * @param commondata 
	 * @return the uuid
	 */
	public String addCommonDataWithUUID (ArrayCollection ac);
	
	/**
	* queryGovenorCode: query table B_GOVENOR_CODE
	* query table B_GOVENOR_CODE, obtain values of table, then packaging to list
	* @return List< HashMap > list after query
	* @throws 
	*/
	public ArrayCollection queryGovenorCode () ;
	/**
	* queryTechnicalCommitteesCode: query talbe TECHNICAL_COMMITTEES_CODE 
	* query table TECHNICAL_COMMITTEES_CODE, obtain values of table, 
	* then packaging to list
	* @return List< HashMap >list after query
	* @throws 
	*/
	 public ArrayCollection queryTechnicalCommitteesCode () ;
	
	/**
	* addGantt: add plan start date, end date
	* add start date and end date to table B_GANTT
	* @param gantt  plan start date and end date
	* @return Boolean  if add successfully return true, failure return false
	* @throws 
	*/
	public Boolean  addGantt (HashMap  gantt);
	/**
	* queryCommonData: query table B_COMMONDATA
	* query table B_COMMONDATA to obtain list
	* @param uuid B_CLASS_FIELD of uuid
	* @return List< HashMap > data queried packaged to list
	* @throws 
	*/
	public HashMap queryCommonData (String classfieldfuncuuid,String commondatauuid);

	/**
	* updateCommonData: modify table B_COMMONDATA
	* obtain data of table B_COMMONDATA by  queryCommonData (String uuid),
	* modify table B_COMMONDATA by updateCommonData (List< HashMap >  commondata)
	* @param commondata list
	* @return Boolean  if modify successfully return true, failure return false
	* @throws 
	*/
	public Boolean updateCommonData (ArrayCollection  commondata);

	/**
	* queryGovenorCode: query table B_GOVENOR_CODE
	* obtain corresponding value according uuid query table B_GOVENOR_CODE
	* @param uuid table B_GOVENOR_CODE of uuid
	* @return String value got
	* @throws 
	*/
	public String queryGovenorCode(String uuid) ;
	/**
	* queryTechnicalCommitteesCode: query talbe B_TECHNICAL_COMMITTEES_CODE
	* obtain corresponding value according uuid query table B_TECHNICAL_COMMITTEES_CODE
	* @param uuid table B_TECHNICAL_COMMITTEES_CODE of uuid
	* @return String value got
    * @throws 
	*/
	public String queryTechnicalCommitteesCode(String uuid);
	
	/**
	 * get record id
	 * 
	 * @param table
	 */
	public int getRecordid(String table);

}
