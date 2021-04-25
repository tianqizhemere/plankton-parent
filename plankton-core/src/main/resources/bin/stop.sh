#!/bin/bash
# Windows编写的shell脚本在Linux可能出现回车换行的错误
# 解密方案：vim -b stop.sh 删除每行后面^M
Port=18081 # 指定访问端口
ID=`ps -ef | grep $Port | grep -v "grep" | awk '{print $2}'`

echo $ID
echo "---------------"
for id in $ID
do
kill -s 9 $id
echo "killed $id"
done
echo "---------------"