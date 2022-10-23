# FROM: 第一条指令必须是FROM指令 (环境依赖 jdk 版本等)
# VOLUME: 作用是创建在本地主机或其他容器可以挂载的数据卷，用来存放数据。
# ARG: 定义一个变量
# JAR_FILE: 为pom文件中项目定义的路径地址
# COPY: 复制本地主机src目录或文件到容器的desc目录，desc不存在时会自动创建。
FROM openjdk:8-jdk-alpine
VOLUME /logs
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# EXPOSE
# 格式为 EXPOSE &lt;port&gt; [&lt;port&gt;...]。
# 告诉 Docker 服务端容器暴露的端口号，供互联系统使用。在启动容器时需要通过 -P，Docker 主机会自动分配一个端口转发到指定的端口
EXPOSE 8080
# ENV
# 格式为 ENV &lt;key&gt; &lt;value&gt;。 指定一个环境变量，会被后续 RUN 指令使用，并在容器运行时保持。
# 例如
#   ENV PG_MAJOR 9.3
#   ENV PG_VERSION 9.3.4
#   RUN curl -SL http://example.com/postgres-$PG_VERSION.tar.xz | tar -xJC /usr/src/postgress &amp;&amp; …
#   ENV PATH /usr/local/postgres-$PG_MAJOR/bin:$PATH
ENV JAVA_OPTS "-Xms128m -Xmx256m"
# ENV PROFILE "dev"
# CMD
# 支持三种格式
#     CMD ["executable","param1","param2"] 使用 exec 执行，推荐方式；
#     CMD command param1 param2 在 /bin/sh 中执行，提供给需要交互的应用；
#     CMD ["param1","param2"] 提供给 ENTRYPOINT 的默认参数；
# 指定启动容器时执行的命令，每个 Dockerfile 只能有一条 CMD 命令。如果指定了多条命令，只有最后一条会被执行。
# 如果用户启动容器时候指定了运行的命令，则会覆盖掉 CMD 指定的命令。
# CMD ["java","${JAVA_OPTS}","-jar","/app.jar"]
# ENTRYPOINT
# 两种格式：
#     ENTRYPOINT ["executable", "param1", "param2"]
#     ENTRYPOINT command param1 param2（shell中执行）。
# 配置容器启动后执行的命令，并且不可被 docker run 提供的参数覆盖。
# 每个 Dockerfile 中只能有一个 ENTRYPOINT，当指定多个时，只有最后一个起效。
ENTRYPOINT java
-XX:ErrorFile=./logs/hs_err_pid%p.log
-Xloggc:./logs/gc.log
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Djava.security.egd=file:/dev/./urandom
-Duser.timezone=GMT+08 ${JAVA_OPTS} -jar /app.jar



