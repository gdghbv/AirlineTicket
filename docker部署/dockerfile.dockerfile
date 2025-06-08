FROM openjdk:17
ENV TZ=Asia/Guangzhou
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY AirlineTicket_IDEA-v1.jar /application.jar
ENTRYPOINT [ "java","-jar","/application.jar" ]