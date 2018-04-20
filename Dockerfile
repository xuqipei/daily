#拉取Java8镜像
FROM intelligentpathways/java8
#设置工作目录为/web
WORKDIR /web
#应用文件复制到/web里
ADD target/daily-0.0.1-SNAPSHOT.jar /web
#暴露端口
EXPOSE 8080
#执行命令启动应用
CMD ["java","-jar","daily-0.0.1-SNAPSHOT.jar"]