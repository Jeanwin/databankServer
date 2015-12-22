#!/bin/sh
. ./db_conf.sh
db2 connect to $db_name user $db_user using $db_password
db2 set current schema $db_schema
db2 -tvf db_alter.sql -z db_alter.log