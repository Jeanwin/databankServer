update U_PORTAL_FUNC set FUNCDESC='Advanced Search' where UUID='0902';
update U_PORTAL_FUNC set FUNCPATH='otherinfo',FUNCNAME='信息页面' where UUID='1303';
DELETE FROM U_PORTAL_FUNC WHERE UUID='0903';
INSERT INTO U_PORTAL_FUNC
(UUID, PID, CLASSUUID, FUNCCODE, FUNCNAME, FUNCDESC, FUNCPATH, FUNCICON, NODETYPE, FUNCTYPE, FUNCSCOPE, FUNCINDEX, STATE, ISDELETE, FUNC_CN_NAME, FUNC_CN_DESC)
VALUES('0903', '09', '09', NULL, '高级搜索&生成报告', 'Advanced Search & Report', 'report', NULL, '3', '2', '1', 4, '1', NULL, NULL, NULL);

commit;