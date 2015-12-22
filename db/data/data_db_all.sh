#!/bin/sh
. ./db_conf.sh
db2 connect to $db_name user $db_user using $db_password
db2 set current schema $db_schema
db2 -tvf B_CLASS_FIELD.sql -z db_data.log
db2 -tvf B_COMMONDATA.sql -z db_data.log
db2 -tvf U_PORTAL_FUNC.sql -z db_data.log
db2 -tvf U_PORTAL_ROLE.sql -z db_data.log
db2 -tvf U_PORTAL_REFROLEFUNC.sql -z db_data.log
db2 -tvf U_PORTAL_REFROLEUSER.sql -z db_data.log
