#!/bin/bash
#功能说明：本功能用于备份mysql数据库
#编写日期：2021-2-24 此处不用修改 mysql安装后基本都在以下文件夹
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:/usr/local/mysql/bin
export PATH
#数据库用户名
dbuser='tianqi'
#数据库密码
dbpasswd='rts123456'
#数据库名,可以定义多个数据库，中间以空格隔开，如：test test1 test2
dbname='plankton'
#备份时间
backtime=`date +%Y%m%d%H%M%S`
#日志备份路径 此处指向你创建的文件夹
logpath='/home/data/mysqlbck/logs'
#数据备份路径 此处指向你创建的文件夹
datapath='/home/data/mysqlbck/data'
#日志记录头部
echo ‘”备份时间为${backtime},备份数据库表 ${dbname} 开始” >> ${logpath}/mysqllog.log
#正式备份数据库
for table in $dbname;
do
source=`mysqldump  -u${dbuser} -p${dbpasswd} --single-transaction ${table}> ${datapath}/${backtime}.sql` 2>> ${logpath}/mysqllog.log;
#备份成功以下操作
if [ "$?" == 0 ];then
cd $datapath
#为节约硬盘空间，将数据库压缩
tar jcf ${table}${backtime}.tar.bz2 ${backtime}.sql > /dev/null
#删除原始文件，只留压缩后文件
rm -f ${datapath}/${backtime}.sql
#删除30天前备份
find $datapath -name "*.tar.bz2" -type f -mtime +90 -exec rm -rf {} \; > /dev/null 2>&1
echo “数据库表 ${dbname} 备份成功!!” >> ${logpath}/mysqllog.log
else
#备份失败则进行以下操作
echo “数据库表 ${dbname} 备份失败!!” >> ${logpath}/mysqllog.log
fi
done