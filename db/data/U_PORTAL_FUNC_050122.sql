delete from U_PORTAL_FUNC where UUID in ('0902','1102');

insert into U_PORTAL_FUNC(UUID,PID,CLASSUUID,FUNCNAME,FUNCDESC,FUNCPATH,NODETYPE,FUNCTYPE,FUNCSCOPE,FUNCINDEX,STATE) 
values('0902','09','09','高级搜索','Search & Report','advanceSearch','3','2','1',3,'1');

insert into U_PORTAL_FUNC(UUID,PID,CLASSUUID,FUNCNAME,FUNCDESC,FUNCPATH,NODETYPE,FUNCTYPE,FUNCSCOPE,FUNCINDEX,STATE) 
values('1102','11','11','下载列表','Download List','downloadList','3','2','1',3,'1');

update U_PORTAL_FUNC set FUNCDESC='Simple Search' where uuid='0901';
commit;