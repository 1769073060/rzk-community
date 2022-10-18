# rzk-community
睿共享社区
name="rzk-community"
cd /root/.jenkins/workspace/$name
cp ./target/$name.jar $name.jar
docker stop $name || true
docker rm test  || true
docker rmi $name  || true
docker build -t $name --build-arg JAR_FILE=$name.jar .
docker run -dit --restart always --name tmip -p 80:8080 -v /home/logs:/logs -e JAVA_OPTS="-Xms128m -Xmx256m -Dspring.profiles.active=dev" $name



#Jekins构建完成后会自动关闭进程及其子进程，加上这一句可以避免自动关闭
BUILD_ID=DONTKILLME
# 下面这一句代表该文件使用的是bash语法
#!/bin/bash
#获取你想运行jar包的进程号，grep -v意为不包括（grep -v grep指的是不包括grep下的所有信息），awk '{print $2}'意为取第二个字段输出，赋值给pid
pid=`ps -ef | grep rzk-community.jar | grep -v grep | awk '{print $2}'`
#如果存在则把该进程杀掉，echo表示输出日志，$符号表示获取变量的值
if [ -n "$pid" ]
then
echo "kill -9 的pid:" $pid
kill -9 $pid
fi
echo "复制jar包"
#把jenkins打的jar包复制到自己指定的目录下
cp  /root/.jenkins/workspace/父工程项目名称/子工程项目名称/target/rzk-community.jar /自己指定的目录路径/
echo "启动jar包"
#最后启动jar包并把日志输出到指定的文件中以便查看
nohup java -jar /自己指定的目录路径/rzk-community.jar > /自己指定的目录路径/credit_manage.log &


#Jekins构建完成后会自动关闭进程及其子进程，加上这一句可以避免自动关闭
BUILD_ID=DONTKILLME
# 下面这一句代表该文件使用的是bash语法
#!/bin/bash
#获取你想运行jar包的进程号，grep -v意为不包括（grep -v grep指的是不包括grep下的所有信息），awk '{print $2}'意为取第二个字段输出，赋值给pid
pid=`ps -ef | grep rzk-community.jar | grep -v grep | awk '{print $2}'`
#如果存在则把该进程杀掉，echo表示输出日志，$符号表示获取变量的值
if [ -n "$pid" ]
then
echo "kill -9 的pid:" $pid
kill -9 $pid
fi
echo "复制jar包"
#把jenkins打的jar包复制到自己指定的目录下
rm -rf /opt/jar/rzk-community/lib/
rm -rf /opt/jar/rzk-community/rzk-community-api.jar
cp -rf /root/.jenkins/workspace/rzk-community/rzk-community-api/target/lib/ /opt/jar/rzk-community/lib/
cp  /root/.jenkins/workspace/rzk-community/rzk-community-api/target/rzk-community-api.jar /opt/jar/rzk-community/
echo "启动jar包"
#最后启动jar包并把日志输出到指定的文件中以便查看
nohup java -Xms800m -Xmx800m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:MaxNewSize=512m -jar /opt/jar/rzk-community/rzk-community-api.jar --server.port=8099 -Dspring.config.additional-location=/opt/jar/rzk-community/config/application.yml >> /usr/local/nohup.out 2>&1 &
# --spring.profiles.active=dev   > /usr/local/nohup.out 2>&1 &