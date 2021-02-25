export JAVA_HOME=/usr/local/jdk  # 配置jdk路径
export PATH=$JAVA_HOME/bin:$PATH 
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

Port=18081 # 指定访问端口
JarName=plankton-web-service-1.0.0-SNAPSHOT.jar  # 指定SpringBoot打包jar包名称
LogsPatch=./logs_$Port  # 日志路径
ID=`ps -ef | grep $Port | grep -v "grep" | awk '{print $2}'`  

echo $ID  
echo "---------------"  
for id in $ID  
do  
kill -s 9 $id  
echo "killed $id"  
done  
echo "---------------"  

rm -rf $LogsPatch
mkdir $LogsPatch

export LANG=zh_CN.UTF-8

set -m 

nohup java -jar -Dlogging.path=$LogsPatch $JarName>$LogsPatch/catlina.out 2>&1 &

tail -f $LogsPatch/catlina.out









































