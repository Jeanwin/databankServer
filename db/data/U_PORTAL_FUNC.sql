delete from U_PORTAL_FUNC where UUID in ('0902','1102');

insert into U_PORTAL_FUNC(UUID,PID,CLASSUUID,FUNCNAME,FUNCDESC,FUNCPATH,NODETYPE,FUNCTYPE,FUNCSCOPE,FUNCINDEX,STATE) 
values('0902','09','09','高级搜索','Search & Report','advanceSearch','3','2','1',3,'1');

insert into U_PORTAL_FUNC(UUID,PID,CLASSUUID,FUNCNAME,FUNCDESC,FUNCPATH,NODETYPE,FUNCTYPE,FUNCSCOPE,FUNCINDEX,STATE) 
values('1102','11','11','下载列表','Download List','downloadList','3','2','1',3,'1');

update U_PORTAL_FUNC set FUNCDESC='Simple Search' where uuid='0901';


DELETE FROM U_PORTAL_FUNC WHERE UUID='17';

INSERT INTO U_PORTAL_FUNC
(UUID, PID, CLASSUUID, FUNCCODE, FUNCNAME, FUNCDESC, FUNCPATH, FUNCICON, NODETYPE, FUNCTYPE, FUNCSCOPE, FUNCINDEX, STATE, ISDELETE, FUNC_CN_NAME, FUNC_CN_DESC)
VALUES('17', '0', '17', NULL, '管理', 'Admin', NULL, NULL, '2', '2', '2', 7, '1', NULL, NULL, NULL);

DELETE FROM U_PORTAL_FUNC WHERE UUID='152AD30D-5E59-652A-FD5D-53BA83AE71C9';

INSERT INTO U_PORTAL_FUNC
(UUID, PID, CLASSUUID, FUNCCODE, FUNCNAME, FUNCDESC, FUNCPATH, FUNCICON, NODETYPE, FUNCTYPE, FUNCSCOPE, FUNCINDEX, STATE, ISDELETE, FUNC_CN_NAME, FUNC_CN_DESC)
VALUES('152AD30D-5E59-652A-FD5D-53BA83AE71C9', '01', '01', NULL, 'What''s hot', '', NULL, NULL, '2', '1', NULL, 0, '2', NULL, '热点新闻', '热点新闻');


update u_portal_func t set t.ISDELETE = '1' where t.PID ='06';
update u_portal_func t set t.ISDELETE = '0',t.FUNCDESC='Home' where t.UUID ='0601';
update u_portal_func t set t.FUNCDESC='Home' where t.UUID ='06';
update u_portal_func t set t.ISDELETE = '1' where t.UUID ='10';
update u_portal_func t set t.ISDELETE = '1' where t.UUID ='1001';
update u_portal_func t set t.ISDELETE = '1' where t.UUID ='13';
update u_portal_func t set t.ISDELETE = '1' where t.UUID ='15';
update U_PORTAL_FUNC set FUNCDESC='Advanced Search & Report' where UUID='0902';

update u_portal_func t set t.ISDELETE = '1' where t.UUID ='1501';
update u_portal_func t set t.PID = '17' where t.UUID ='1501';
update u_portal_func t set t.PID = '17' where t.UUID ='1301';
update u_portal_func t set t.PID = '17' where t.UUID ='1302';
update u_portal_func t set t.FUNCDESC = 'Module Configuration' where t.UUID ='1301';
update u_portal_func t set t.FUNCDESC = 'Field Configuration' where t.UUID ='1302';
update u_portal_func t set t.FUNCDESC = 'User Management' where t.UUID ='1501';
update u_portal_func t set t.FUNCDESC = 'Info Page' where t.UUID ='1303';
update u_portal_func t set t.FUNCDESC = 'News',t.FUNCNAME='News',t.FUNC_CN_NAME='新闻' where t.UUID ='01';

update u_portal_func t set t.FUNCPATH = 'notice' where t.UUID ='1303';
update u_portal_func t set t.STATE = '1' where t.UUID ='152AD30D-5E59-652A-FD5D-53BA83AE71C9';
update u_portal_func t set t.PID='17',t.FUNCDESC='Forum Configuration' where t.UUID='1304';

update u_portal_func t set t.FUNCINDEX='0' where uuid ='1501';
update u_portal_func t set t.FUNCINDEX='1' where uuid ='1301';
update u_portal_func t set t.FUNCINDEX='2' where uuid ='1302';
update u_portal_func t set t.FUNCINDEX='3' where uuid ='1304';
update u_portal_func t set t.ISDELETE='0' where uuid ='1501';

update U_PORTAL_FUNC set FUNCDESC='Advanced Search' where UUID='0902';
update U_PORTAL_FUNC set FUNCPATH='otherinfo',FUNCNAME='信息页面' where UUID='1303';
DELETE FROM U_PORTAL_FUNC WHERE UUID='0903';
INSERT INTO U_PORTAL_FUNC
(UUID, PID, CLASSUUID, FUNCCODE, FUNCNAME, FUNCDESC, FUNCPATH, FUNCICON, NODETYPE, FUNCTYPE, FUNCSCOPE, FUNCINDEX, STATE, ISDELETE, FUNC_CN_NAME, FUNC_CN_DESC)
VALUES('0903', '09', '09', NULL, '高级搜索&生成报告', 'Advanced Search & Report', 'report', NULL, '3', '2', '1', 4, '1', NULL, NULL, NULL);


commit;