CREATE BUFFERPOOL "BPTMP8K" SIZE AUTOMATIC PAGESIZE 8192;

CREATE BUFFERPOOL "BPTMP32K" SIZE AUTOMATIC PAGESIZE 32768;

CREATE BUFFERPOOL "BPUSER8K" SIZE AUTOMATIC PAGESIZE 8192;

CREATE BUFFERPOOL "BPUSER32K" SIZE AUTOMATIC PAGESIZE 32768;


------------------------------------
-- DDL Statements for TABLESPACES --
------------------------------------


CREATE LARGE TABLESPACE "SYSTOOLSPACE" IN DATABASE PARTITION GROUP IBMCATGROUP 
	 PAGESIZE 4096 MANAGED BY AUTOMATIC STORAGE 
	 AUTORESIZE YES 
	 INITIALSIZE 32 M 
	 MAXSIZE NONE 
	 EXTENTSIZE 4
	 PREFETCHSIZE AUTOMATIC
	 BUFFERPOOL IBMDEFAULTBP
	 OVERHEAD 7.500000
	 TRANSFERRATE 0.060000 
	 NO FILE SYSTEM CACHING  
	 DROPPED TABLE RECOVERY ON;

CREATE LARGE TABLESPACE "BANKTBS8K" IN DATABASE PARTITION GROUP IBMDEFAULTGROUP PAGESIZE 8192 MANAGED BY DATABASE 
	 USING (FILE '/dbdata/DATABANK/BANKTBS/BANKTBS8K01.DBF' 70000,
		FILE '/dbdata/DATABANK/BANKTBS/BANKTBS8K02.DBF' 70000,
		FILE '/dbdata/DATABANK/BANKTBS/BANKTBS8K03.DBF' 70000)
	 EXTENTSIZE 32
	 PREFETCHSIZE AUTOMATIC
	 BUFFERPOOL BPUSER8K
	 OVERHEAD 7.500000
	 TRANSFERRATE 0.060000 
	 NO FILE SYSTEM CACHING  
	 DROPPED TABLE RECOVERY ON;

CREATE LARGE TABLESPACE "BANKTBS32K" IN DATABASE PARTITION GROUP IBMDEFAULTGROUP PAGESIZE 32768 MANAGED BY DATABASE 
	 USING (FILE '/dbdata/DATABANK/BANKTBS/BANKTBS32K01.DBF' 20000,
		FILE '/dbdata/DATABANK/BANKTBS/BANKTBS32K02.DBF' 20000,
		FILE '/dbdata/DATABANK/BANKTBS/BANKTBS32K03.DBF' 20000)
	 EXTENTSIZE 32
	 PREFETCHSIZE AUTOMATIC
	 BUFFERPOOL BPUSER32K
	 OVERHEAD 7.500000
	 TRANSFERRATE 0.060000 
	 NO FILE SYSTEM CACHING  
	 DROPPED TABLE RECOVERY ON;

CREATE TEMPORARY TABLESPACE "TMPTBS32K" IN DATABASE PARTITION GROUP IBMTEMPGROUP PAGESIZE 32768 MANAGED BY SYSTEM 
	 USING ('/dbdata/DATABANK/BANKTBS/TEMPTBS32K')
	 EXTENTSIZE 32
	 PREFETCHSIZE AUTOMATIC
	 BUFFERPOOL BPTMP32K
	 OVERHEAD 7.500000
	 TRANSFERRATE 0.060000 
	 FILE SYSTEM CACHING  
	 DROPPED TABLE RECOVERY OFF;


CREATE USER TEMPORARY TABLESPACE "SYSTOOLSTMPSPACE" IN DATABASE PARTITION GROUP IBMCATGROUP 
	 PAGESIZE 4096 MANAGED BY AUTOMATIC STORAGE 
	 EXTENTSIZE 4
	 PREFETCHSIZE AUTOMATIC
	 BUFFERPOOL IBMDEFAULTBP
	 OVERHEAD 7.500000
	 TRANSFERRATE 0.060000 
	 FILE SYSTEM CACHING  
	 DROPPED TABLE RECOVERY OFF;


-- Mimic tablespace

ALTER TABLESPACE SYSCATSPACE
      PREFETCHSIZE AUTOMATIC
      OVERHEAD 7.500000
      NO FILE SYSTEM CACHING 
      AUTORESIZE YES 
      TRANSFERRATE 0.060000;


ALTER TABLESPACE TEMPSPACE1
      PREFETCHSIZE AUTOMATIC
      OVERHEAD 7.500000
      FILE SYSTEM CACHING 
      TRANSFERRATE 0.060000;


ALTER TABLESPACE USERSPACE1
      PREFETCHSIZE AUTOMATIC
      OVERHEAD 7.500000
      NO FILE SYSTEM CACHING 
      AUTORESIZE YES 
      TRANSFERRATE 0.060000;


------------------------------------------------
-- DDL Statements for Schemas
------------------------------------------------


CREATE SCHEMA "DATABANK" AUTHORIZATION "DB2INST1";


---------------------------------
-- DDL Statements for Sequences
---------------------------------


CREATE SEQUENCE "DATABANK"."S_USER_NEXT_ID" AS BIGINT
	MINVALUE 1000 MAXVALUE 9223372036854775807
	START WITH 1000 INCREMENT BY 1
	CACHE 20 NO CYCLE NO ORDER;



------------------------------------------------
-- DDL Statements for table "DATABANK"."B_GOVENOR_CODE"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_GOVENOR_CODE"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "GOVENOR_CODE" VARCHAR(200) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."B_GOVENOR_CODE"

ALTER TABLE "DATABANK"."B_GOVENOR_CODE" 
	ADD CONSTRAINT "PK_B_GOVENOR_CODE" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."B_TECHNICAL_COMMITTEES_CODE"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_TECHNICAL_COMMITTEES_CODE"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "TECHNICAL_COMMITTEES_CODE" VARCHAR(200) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."B_TECHNICAL_COMMITTEES_CODE"

ALTER TABLE "DATABANK"."B_TECHNICAL_COMMITTEES_CODE" 
	ADD CONSTRAINT "PK_B_TECHNICAL_COMMITTEES_CODE" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."B_GANTT_APPENDIX"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_GANTT_APPENDIX"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "GANTT_UUID" VARCHAR(36) , 
		  "COMMONDATA_UUID" VARCHAR(36) , 
		  "CURRENT_DATE" DECIMAL(20,0) , 
		  "FILE_NAME" VARCHAR(500) , 
		  "DESCRIPTION" VARCHAR(1000) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."B_GANTT_APPENDIX"

ALTER TABLE "DATABANK"."B_GANTT_APPENDIX" 
	ADD CONSTRAINT "PK_B_GANTT_APPENDIX" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "CLASS_ATTRIBUTE_PAGE_UUID" VARCHAR(36) , 
		  "LINK_TITLE" VARCHAR(200) , 
		  "LINK_APPENDIX_FILENAME" VARCHAR(200) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX"

ALTER TABLE "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX" 
	ADD CONSTRAINT "PK_B_CLASS_ATTRIBUTE_APPENDIX" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."B_GANTT"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_GANTT"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "RECORDID" DECIMAL(20,0) , 
		  "COMMONDATA_UUID" VARCHAR(36) , 
		  "START_DATE" DECIMAL(20,0) , 
		  "END_DATE" DECIMAL(20,0) , 
		  "PHASE" DECIMAL(20,0) , 
		  "INFORMATION" VARCHAR(3000) , 
		  "REMARK" VARCHAR(3000) , 
		  "STATE" VARCHAR(36) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."B_GANTT"

ALTER TABLE "DATABANK"."B_GANTT" 
	ADD CONSTRAINT "PK_B_GANTT" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."B_COMMONDATA_FIELD"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_COMMONDATA_FIELD"  (
		  "UUID" VARCHAR(36) , 
		  "FIELD_NAME" VARCHAR(200) )   
		 IN "BANKTBS8K" ; 





------------------------------------------------
-- DDL Statements for table "DATABANK"."B_CLASS_ATTRIBUTE_PAGE"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_CLASS_ATTRIBUTE_PAGE"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "FUNC_UUID" VARCHAR(36) , 
		  "TITLE" VARCHAR(200) , 
		  "PICTURE_PATHNAME" VARCHAR(500) , 
		  "CONTENT" VARCHAR(2000) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."B_CLASS_ATTRIBUTE_PAGE"

ALTER TABLE "DATABANK"."B_CLASS_ATTRIBUTE_PAGE" 
	ADD CONSTRAINT "PK_B_CLASS_ATTRIBUTE_PAGE" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."B_CLASS_FIELD"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_CLASS_FIELD"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "FUNC_UUID" VARCHAR(36) , 
		  "FIELD_NAME" VARCHAR(200) , 
		  "SHOW_NAME_CH" VARCHAR(200) , 
		  "SHOW_NAME_EN" VARCHAR(200) , 
		  "ORDER_NO" DECIMAL(20,0) , 
		  "SHOW_MODE" DECIMAL(20,0) , 
		  "IS_PROTECTED" VARCHAR(12) , 
		  "FIELD_TYPE" VARCHAR(50) , 
		  "FIELD_VALUE" VARCHAR(1000) , 
		  "IS_CONFIG" VARCHAR(12) , 
		  "TOOLTIP" VARCHAR(1000) , 
		  "REGEX" VARCHAR(100) , 
		  "SHOW_WIDTH" DECIMAL(12,0) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."B_CLASS_FIELD"

ALTER TABLE "DATABANK"."B_CLASS_FIELD" 
	ADD CONSTRAINT "PK_B_CLASS_FIELD" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_PORTAL_REFORGUSER"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_PORTAL_REFORGUSER"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "ORGUUID" VARCHAR(36) , 
		  "ORGCODE" VARCHAR(128) , 
		  "ORGNAME" VARCHAR(256) , 
		  "USERUUID" VARCHAR(36) , 
		  "USERCODE" VARCHAR(128) , 
		  "USERNAME" VARCHAR(128) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_PORTAL_REFORGUSER"

ALTER TABLE "DATABANK"."U_PORTAL_REFORGUSER" 
	ADD CONSTRAINT "PK_U_PORTAL_REFORGUSER" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_PORTAL_REFROLEUSER"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_PORTAL_REFROLEUSER"  (
		  "UUID" VARCHAR(36) , 
		  "ROLEUUID" VARCHAR(36) , 
		  "ROLECODE" VARCHAR(128) , 
		  "ROLENAME" VARCHAR(128) , 
		  "USERUUID" VARCHAR(36) , 
		  "USERCODE" VARCHAR(128) , 
		  "USERNAME" VARCHAR(128) )   
		 IN "BANKTBS8K" ; 





------------------------------------------------
-- DDL Statements for table "DATABANK"."U_USER_DOWNLOAD_LOG"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_USER_DOWNLOAD_LOG"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "USER_LOG_UUID" VARCHAR(36) , 
		  "DOWNLOADFILEPATH" VARCHAR(500) , 
		  "LAST_MODIFY_AUTHOR" VARCHAR(50) , 
		  "LAST_MODIFY_DATE_STR" VARCHAR(50) , 
		  "DOWNLOADTIMESTR" VARCHAR(50) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_USER_DOWNLOAD_LOG"

ALTER TABLE "DATABANK"."U_USER_DOWNLOAD_LOG" 
	ADD CONSTRAINT "PK_U_USER_DOWNLOAD_LOG" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_PORTAL_REFROLEFUNC"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_PORTAL_REFROLEFUNC"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "ROLEUUID" VARCHAR(36) NOT NULL , 
		  "ROLECODE" VARCHAR(128) , 
		  "ROLENAME" VARCHAR(128) , 
		  "FUNCUUID" VARCHAR(36) , 
		  "FUNCCODE" VARCHAR(128) , 
		  "FUNCNAME" VARCHAR(256) , 
		  "OPERATEUUID" VARCHAR(36) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_PORTAL_REFROLEFUNC"

ALTER TABLE "DATABANK"."U_PORTAL_REFROLEFUNC" 
	ADD CONSTRAINT "PK_U_PORTAL_REFROLEFUNC" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_PORTAL_ORG"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_PORTAL_ORG"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "PID" VARCHAR(36) , 
		  "ORGCODE" VARCHAR(128) , 
		  "ORGNAME" VARCHAR(256) , 
		  "ORGDESC" VARCHAR(256) , 
		  "STATE" VARCHAR(36) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_PORTAL_ORG"

ALTER TABLE "DATABANK"."U_PORTAL_ORG" 
	ADD CONSTRAINT "PK_U_PORTAL_ORG" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_PORTAL_ROLE"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_PORTAL_ROLE"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "PID" VARCHAR(36) , 
		  "ROLECODE" VARCHAR(128) , 
		  "ROLENAME" VARCHAR(128) , 
		  "ROLEDESC" VARCHAR(256) , 
		  "STATE" VARCHAR(36) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_PORTAL_ROLE"

ALTER TABLE "DATABANK"."U_PORTAL_ROLE" 
	ADD CONSTRAINT "PK_U_PORTAL_ROLE" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_OPERATE"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_OPERATE"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "OPERATE_CODE" VARCHAR(200) , 
		  "OPERATE_NAME" VARCHAR(200) , 
		  "STATE" VARCHAR(36) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_OPERATE"

ALTER TABLE "DATABANK"."U_OPERATE" 
	ADD CONSTRAINT "PK_U_OPERATE" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_PORTAL_FUNC"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_PORTAL_FUNC"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "PID" VARCHAR(36) NOT NULL , 
		  "CLASSUUID" VARCHAR(36) NOT NULL , 
		  "FUNCCODE" VARCHAR(128) , 
		  "FUNCNAME" VARCHAR(256) , 
		  "FUNCDESC" VARCHAR(256) , 
		  "FUNCPATH" VARCHAR(1024) , 
		  "FUNCICON" VARCHAR(1024) , 
		  "NODETYPE" VARCHAR(36) , 
		  "FUNCTYPE" VARCHAR(36) , 
		  "FUNCSCOPE" VARCHAR(36) , 
		  "FUNCINDEX" DECIMAL(20,0) , 
		  "STATE" VARCHAR(36) , 
		  "ISDELETE" VARCHAR(36) , 
		  "FUNC_CN_NAME" VARCHAR(256) , 
		  "FUNC_CN_DESC" VARCHAR(256) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_PORTAL_FUNC"

ALTER TABLE "DATABANK"."U_PORTAL_FUNC" 
	ADD CONSTRAINT "PK_U_PORTAL_FUNC" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."B_COMMONDATA"
------------------------------------------------
 

CREATE TABLE "DATABANK"."B_COMMONDATA"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "RECORDID" DECIMAL(20,0) , 
		  "FUNC_UUID" VARCHAR(36) , 
		  "EFFECT_STATUS" VARCHAR(10) , 
		  "TITLE_ENGLISH" VARCHAR(2000) , 
		  "TITLE_EN" VARCHAR(2000) , 
		  "TITLE_CHINESE" VARCHAR(2000) , 
		  "TITLE_CH" VARCHAR(2000) , 
		  "ISSUANCE_DATE_STR" VARCHAR(50) , 
		  "ISSUANCE_DATE" DECIMAL(20,0) , 
		  "EXECUTE_DATE_STR" VARCHAR(50) , 
		  "EXECUTE_DATE" DECIMAL(20,0) , 
		  "PRE_EXECUTE_DATE_STR" VARCHAR(50) , 
		  "PRE_EXECUTE_DATE" DECIMAL(20,0) , 
		  "REMARK_" VARCHAR(1000) , 
		  "STANDARD_ATTRIBUTE1" VARCHAR(100) , 
		  "STANDARD_ATTRIBUTE2" VARCHAR(100) , 
		  "STANDARD_ATTRIBUTE3" VARCHAR(100) , 
		  "NO_" VARCHAR(128) , 
		  "VERSION_" VARCHAR(10) , 
		  "ADOPT_NATIONAL_STANDARD_NO" VARCHAR(100) , 
		  "APPLICATION_DEGREE" VARCHAR(100) , 
		  "GORVERNOR_CODE_UUID" VARCHAR(36) , 
		  "TECHNICAL_COMMITTEES_CODE_UUID" VARCHAR(36) , 
		  "DRAFTING_COMMITTEE" VARCHAR(100) , 
		  "PLAN_NO" VARCHAR(100) , 
		  "ONLINE_AUTO_EXECUTE_DATE_STR" VARCHAR(50) , 
		  "ONLINE_AUTO_EXECUTE_DATE" DECIMAL(20,0) , 
		  "DOWNLOAD_GB_EN_TITLE" VARCHAR(200) , 
		  "DOWNLOAD_GB_EN" VARCHAR(500) , 
		  "DOWNLOAD_GB_EN_SWF" VARCHAR(500) , 
		  "DOWNLOAD_GB_CH_TITLE" VARCHAR(200) , 
		  "DOWNLOAD_GB_CH" VARCHAR(500) , 
		  "DOWNLOAD_GB_CH_SWF" VARCHAR(500) , 
		  "DOWNLOAD_ECE_R_TITLE" VARCHAR(200) , 
		  "DOWNLOAD_ECE_R" VARCHAR(500) , 
		  "DOWNLOAD_ECE_R_SWF" VARCHAR(500) , 
		  "DOWNLOAD_EEC_TITLE" VARCHAR(200) , 
		  "DOWNLOAD_EEC" VARCHAR(500) , 
		  "DOWNLOAD_EEC_SWF" VARCHAR(500) , 
		  "DOWNLOAD_OTHERS_TITLE" VARCHAR(200) , 
		  "DOWNLOAD_OTHERS" VARCHAR(500) , 
		  "DOWNLOAD_OTHERS_SWF" VARCHAR(500) , 
		  "DOC_TITLE" VARCHAR(200) , 
		  "DOC_" VARCHAR(3000) , 
		  "SUMMARY" VARCHAR(500) , 
		  "SUMMARY_SWF" VARCHAR(500) , 
		  "APPLICABILITY" VARCHAR(200) , 
		  "REPLACED_STANDARD" VARCHAR(500) , 
		  "ISPARTS" VARCHAR(10) , 
		  "ISMANDATORY" VARCHAR(10) , 
		  "ISCCC" VARCHAR(10) , 
		  "ISBULLETIN" VARCHAR(10) , 
		  "DOCUMENT_NO" VARCHAR(500) , 
		  "SORT_" VARCHAR(100) , 
		  "PROMULGATION_DEPARTMENT" VARCHAR(100) , 
		  "SOURCE_" VARCHAR(100) , 
		  "AUTHOR" VARCHAR(100) , 
		  "PAPER" VARCHAR(100) , 
		  "KEY_WORDS" VARCHAR(1000) , 
		  "IMPORTANCE" VARCHAR(36) , 
		  "LAST_MODIFY_AUTHOR" VARCHAR(50) , 
		  "LAST_MODIFY_DATE_STRING" VARCHAR(50) , 
		  "LAST_MODIFY_DATE" DECIMAL(20,0) , 
		  "STATE" VARCHAR(36) , 
		  "DOWNLOAD_FMVSS_TITLE" VARCHAR(200) , 
		  "DOWNLOAD_FMVSS" VARCHAR(500) , 
		  "DOWNLOAD_FMVSS_SWF" VARCHAR(500) , 
		  "REVISION_EST" VARCHAR(20) , 
		  "HOMO_RISK" VARCHAR(10) , 
		  "REFERENCE" VARCHAR(100) , 
		  "EFFECTIVE_DATE" DECIMAL(20,0) , 
		  "IS_IMPLEMENTED" DECIMAL(10,0) , 
		  "IS_NATIONAL_EMI" DECIMAL(10,0) , 
		  "IS_LOCAL_EMI" DECIMAL(10,0) , 
		  "STATUS" VARCHAR(20) , 
		  "CONTACT_PERSON" VARCHAR(20) , 
		  "POWER_TYPE" VARCHAR(200) )   
		 IN "BANKTBS32K" ; 

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."CONTACT_PERSON" IS 'contact person';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."DOWNLOAD_FMVSS" IS 'realname of FMVSS';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."DOWNLOAD_FMVSS_SWF" IS 'swf file of FMVSS';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."DOWNLOAD_FMVSS_TITLE" IS 'title of FMVSS';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."EFFECTIVE_DATE" IS 'Effective date';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."HOMO_RISK" IS 'Homologation Risk';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."IS_IMPLEMENTED" IS 'if it is implemented';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."IS_LOCAL_EMI" IS 'if it is Local Emission';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."IS_NATIONAL_EMI" IS 'if it is National Emission';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."POWER_TYPE" IS 'Power type';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."REFERENCE" IS 'Reference';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."REVISION_EST" IS 'Revision/Newly added';

COMMENT ON COLUMN "DATABANK"."B_COMMONDATA"."STATUS" IS 'doc status';


-- DDL Statements for primary key on Table "DATABANK"."B_COMMONDATA"

ALTER TABLE "DATABANK"."B_COMMONDATA" 
	ADD CONSTRAINT "PK_B_COMMONDATA" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_USER_LOG"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_USER_LOG"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "LOGID" DECIMAL(20,0) , 
		  "USER_UUID" VARCHAR(36) , 
		  "SESSIONID" VARCHAR(128) , 
		  "LOGINCOUNT" DECIMAL(20,0) , 
		  "LOGINTIMESTRING" VARCHAR(50) , 
		  "LOGINTIME" DECIMAL(20,0) , 
		  "LOGOUTTIMESTRING" VARCHAR(50) , 
		  "LOGOUTTIME" DECIMAL(20,0) , 
		  "LOGINIP" VARCHAR(100) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_USER_LOG"

ALTER TABLE "DATABANK"."U_USER_LOG" 
	ADD CONSTRAINT "PK_U_USER_LOG" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."U_PORTAL_USER"
------------------------------------------------
 

CREATE TABLE "DATABANK"."U_PORTAL_USER"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "USERCODE" VARCHAR(128) , 
		  "USERNAME" VARCHAR(128) , 
		  "LOGINNAME" VARCHAR(128) , 
		  "PASSWORD" VARCHAR(128) , 
		  "EMAIL" VARCHAR(200) , 
		  "TELEPHONE" VARCHAR(100) , 
		  "STATE" VARCHAR(36) )   
		 IN "BANKTBS8K" ; 


-- DDL Statements for primary key on Table "DATABANK"."U_PORTAL_USER"

ALTER TABLE "DATABANK"."U_PORTAL_USER" 
	ADD CONSTRAINT "PK_U_PORTAL_USER" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."HP_TB_FORUM_CATEGORY"
------------------------------------------------
 

CREATE TABLE "DATABANK"."HP_TB_FORUM_CATEGORY"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "PID" VARCHAR(36) , 
		  "EN_NAME" VARCHAR(256) NOT NULL , 
		  "EN_DESC" VARCHAR(256) , 
		  "CN_NAME" VARCHAR(256) NOT NULL , 
		  "CN_DESC" VARCHAR(256) , 
		  "ORDER_NUM" INTEGER , 
		  "CREATE_DATE" TIMESTAMP , 
		  "LAST_MODIFY_DATE" TIMESTAMP , 
		  "LAST_MODIFIOR_UUID" VARCHAR(36) , 
		  "IS_DELETE" CHAR(1) )   
		 IN "USERSPACE1" ; 


-- DDL Statements for primary key on Table "DATABANK"."HP_TB_FORUM_CATEGORY"

ALTER TABLE "DATABANK"."HP_TB_FORUM_CATEGORY" 
	ADD CONSTRAINT "HP_PK_FORUM_CATEGORY" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."HP_TB_FORUM_TOPIC"
------------------------------------------------
 

CREATE TABLE "DATABANK"."HP_TB_FORUM_TOPIC"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "PID" VARCHAR(36) NOT NULL , 
		  "TITLE" VARCHAR(256) NOT NULL , 
		  "CONTENT" VARCHAR(256) NOT NULL , 
		  "AUTHOR_UUID" VARCHAR(36) NOT NULL , 
		  "CREATE_DATE" TIMESTAMP NOT NULL , 
		  "LAST_MODIFIOR_UUID" VARCHAR(36) , 
		  "LAST_MODIFY_DATE" TIMESTAMP , 
		  "IS_DELETE" CHAR(1) NOT NULL )   
		 IN "USERSPACE1" ; 


-- DDL Statements for primary key on Table "DATABANK"."HP_TB_FORUM_TOPIC"

ALTER TABLE "DATABANK"."HP_TB_FORUM_TOPIC" 
	ADD CONSTRAINT "HP_PK_FORUM_TOPIC" PRIMARY KEY
		("UUID");


------------------------------------------------
-- DDL Statements for table "DATABANK"."HP_TB_FORUM_REPLY"
------------------------------------------------
 

CREATE TABLE "DATABANK"."HP_TB_FORUM_REPLY"  (
		  "UUID" VARCHAR(36) NOT NULL , 
		  "PID" VARCHAR(36) NOT NULL , 
		  "CONTENT" VARCHAR(256) NOT NULL , 
		  "AUTHOR_UUID" VARCHAR(36) NOT NULL , 
		  "CREATE_DATE" TIMESTAMP NOT NULL , 
		  "LAST_MODIFIOR_UUID" VARCHAR(36) , 
		  "LAST_MODIFY_DATE" TIMESTAMP , 
		  "IS_DELETE" CHAR(1) NOT NULL )   
		 IN "USERSPACE1" ; 


-- DDL Statements for primary key on Table "DATABANK"."HP_TB_FORUM_REPLY"

ALTER TABLE "DATABANK"."HP_TB_FORUM_REPLY" 
	ADD CONSTRAINT "HP_PK_FORUM_REPLY" PRIMARY KEY
		("UUID");


-- DDL Statements for foreign keys on Table "DATABANK"."HP_TB_FORUM_CATEGORY"

ALTER TABLE "DATABANK"."HP_TB_FORUM_CATEGORY" 
	ADD CONSTRAINT "HP_FK_FORUM_CATEGORY_PID" FOREIGN KEY
		("PID")
	REFERENCES "DATABANK"."HP_TB_FORUM_CATEGORY"
		("UUID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- DDL Statements for check constraints on Table "DATABANK"."HP_TB_FORUM_CATEGORY"

ALTER TABLE "DATABANK"."HP_TB_FORUM_CATEGORY" 
	ADD CONSTRAINT "HP_CHECK_FORUM_CATEGORY_UUID_PID" CHECK 
		(UUID <> PID)
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

CREATE TRIGGER "DATABANK"."TRIGGER_USERID" AFTER INSERT ON "DATABANK"."U_PORTAL_USER"
FOR EACH ROW UPDATE DATABANK.U_PORTAL_USER SET DATABANK.U_PORTAL_USER.UUID
= DATABANK.S_USER_NEXT_ID.nextval WHERE DATABANK.U_PORTAL_USER.UUID = '0';

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
--------------------------------------------
-- Authorization Statements on Tables/Views 
--------------------------------------------

 
GRANT DELETE ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_APPENDIX" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_PAGE" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_PAGE" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_PAGE" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_CLASS_ATTRIBUTE_PAGE" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_CLASS_FIELD" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_CLASS_FIELD" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_CLASS_FIELD" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_CLASS_FIELD" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_COMMONDATA" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_COMMONDATA" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_COMMONDATA" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_COMMONDATA" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_COMMONDATA_FIELD" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_COMMONDATA_FIELD" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_COMMONDATA_FIELD" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_COMMONDATA_FIELD" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_GANTT" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_GANTT" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_GANTT" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_GANTT" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_GANTT_APPENDIX" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_GANTT_APPENDIX" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_GANTT_APPENDIX" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_GANTT_APPENDIX" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_GOVENOR_CODE" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_GOVENOR_CODE" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_GOVENOR_CODE" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_GOVENOR_CODE" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."B_TECHNICAL_COMMITTEES_CODE" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."B_TECHNICAL_COMMITTEES_CODE" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."B_TECHNICAL_COMMITTEES_CODE" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."B_TECHNICAL_COMMITTEES_CODE" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."HP_TB_FORUM_CATEGORY" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."HP_TB_FORUM_CATEGORY" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."HP_TB_FORUM_CATEGORY" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."HP_TB_FORUM_CATEGORY" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."HP_TB_FORUM_REPLY" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."HP_TB_FORUM_REPLY" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."HP_TB_FORUM_REPLY" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."HP_TB_FORUM_REPLY" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."HP_TB_FORUM_TOPIC" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."HP_TB_FORUM_TOPIC" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."HP_TB_FORUM_TOPIC" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."HP_TB_FORUM_TOPIC" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_OPERATE" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_OPERATE" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_OPERATE" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_OPERATE" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_PORTAL_FUNC" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_PORTAL_FUNC" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_PORTAL_FUNC" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_PORTAL_FUNC" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_PORTAL_ORG" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_PORTAL_ORG" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_PORTAL_ORG" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_PORTAL_ORG" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_PORTAL_REFORGUSER" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_PORTAL_REFORGUSER" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_PORTAL_REFORGUSER" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_PORTAL_REFORGUSER" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_PORTAL_REFROLEFUNC" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_PORTAL_REFROLEFUNC" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_PORTAL_REFROLEFUNC" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_PORTAL_REFROLEFUNC" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_PORTAL_REFROLEUSER" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_PORTAL_REFROLEUSER" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_PORTAL_REFROLEUSER" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_PORTAL_REFROLEUSER" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_PORTAL_ROLE" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_PORTAL_ROLE" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_PORTAL_ROLE" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_PORTAL_ROLE" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_PORTAL_USER" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_PORTAL_USER" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_PORTAL_USER" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_PORTAL_USER" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_USER_DOWNLOAD_LOG" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_USER_DOWNLOAD_LOG" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_USER_DOWNLOAD_LOG" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_USER_DOWNLOAD_LOG" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_USER_LOG" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_USER_LOG" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_USER_LOG" TO USER "DATABANK" ;

GRANT UPDATE ON TABLE "DATABANK"."U_USER_LOG" TO USER "DATABANK" ;

GRANT DELETE ON TABLE "DATABANK"."U_USER_DOWNLOAD_LIST" TO USER "DATABANK" ;

GRANT INSERT ON TABLE "DATABANK"."U_USER_DOWNLOAD_LIST" TO USER "DATABANK" ;

GRANT SELECT ON TABLE "DATABANK"."U_USER_DOWNLOAD_LIST" TO USER "DATABANK" ;

----------------------------------------
-- Authorization Statements on Database 
----------------------------------------

COMMIT WORK;

CONNECT RESET;

TERMINATE;

