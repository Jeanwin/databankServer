#!/bin/sh
cp db_conf.sh ./alter
cd ./alter
./alter_db.sh

cd ..

cp db_conf.sh ./data
cd ./data
./data_db_all.sh
