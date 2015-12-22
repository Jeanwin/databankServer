ALTER TABLE B_COMMONDATA add column DOWNLOAD_FMVSS_TITLE VARCHAR(200);
comment on column B_COMMONDATA.DOWNLOAD_FMVSS_TITLE is 'title of FMVSS';
ALTER TABLE B_COMMONDATA add column DOWNLOAD_FMVSS VARCHAR(500);
comment on column B_COMMONDATA.DOWNLOAD_FMVSS is 'realname of FMVSS';
ALTER TABLE B_COMMONDATA add column DOWNLOAD_FMVSS_SWF VARCHAR(500);
comment on column B_COMMONDATA.DOWNLOAD_FMVSS_SWF is 'swf file of FMVSS';

ALTER TABLE B_COMMONDATA add column REVISION_EST VARCHAR(20);
comment on column B_COMMONDATA.REVISION_EST is 'Revision/Newly added';
ALTER TABLE B_COMMONDATA add column HOMO_RISK VARCHAR(10);
comment on column B_COMMONDATA.HOMO_RISK is 'Homologation Risk';
ALTER TABLE B_COMMONDATA add column REFERENCE VARCHAR(100);
comment on column B_COMMONDATA.REFERENCE is 'Reference';
ALTER TABLE B_COMMONDATA add column IS_IMPLEMENTED DECIMAL(10);
comment on column B_COMMONDATA.IS_IMPLEMENTED is 'if it is implemented';
ALTER TABLE B_COMMONDATA add column IS_NATIONAL_EMI DECIMAL(10);
comment on column B_COMMONDATA.IS_NATIONAL_EMI is 'if it is National Emission';
ALTER TABLE B_COMMONDATA add column IS_LOCAL_EMI DECIMAL(10);
comment on column B_COMMONDATA.IS_LOCAL_EMI is 'if it is Local Emission';
ALTER TABLE B_COMMONDATA add column STATUS varchar(20);
comment on column B_COMMONDATA.STATUS is 'doc status';
ALTER TABLE B_COMMONDATA add column CONTACT_PERSON varchar(20);
comment on column B_COMMONDATA.CONTACT_PERSON is 'contact person';
--2014-12-12
ALTER TABLE B_COMMONDATA add column POWER_TYPE varchar(200);
comment on column B_COMMONDATA.POWER_TYPE is 'Power type';

--2014-12-26
CREATE TABLE U_USER_DOWNLOAD_LIST  (
		  UUID VARCHAR(36) NOT NULL , 
		  FUNC_UUID VARCHAR(36) , 
		  USER_UUID VARCHAR(36) ,
		  DOCUMENT_TYPE VARCHAR(50) , 
		  DOCUMENT_TITLE VARCHAR(500) , 
		  IS_HISTORY decimal(1) , 
		  ADD_DATE_STR varchar(10),
		  DOWNLOAD_DATE_STR varchar(10)
		  );
		  
ALTER TABLE U_USER_DOWNLOAD_LIST 
	ADD CONSTRAINT PK_U_USER_DOWNLOAD_LIST PRIMARY KEY(UUID);
comment on column U_USER_DOWNLOAD_LIST.UUID is 'PK of table';
comment on column U_USER_DOWNLOAD_LIST.FUNC_UUID is 'To UUID of table u_portal_func';
comment on column U_USER_DOWNLOAD_LIST.USER_UUID is 'To UUID of table u_portal_user';
comment on column U_USER_DOWNLOAD_LIST.DOCUMENT_TYPE is 'Type of document,value:EN/CN/EEC';
comment on column U_USER_DOWNLOAD_LIST.DOCUMENT_TITLE is 'Title of document';
comment on column U_USER_DOWNLOAD_LIST.IS_HISTORY is 'If document has been downloaded,value:1/0';
comment on column U_USER_DOWNLOAD_LIST.ADD_DATE_STR is 'Add date';
comment on column U_USER_DOWNLOAD_LIST.DOWNLOAD_DATE_STR is 'Download date';
commit;
