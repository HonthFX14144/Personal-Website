# Sử dụng hình ảnh Tomcat chính thức
FROM tomcat:9.0

# Copy file WAR vào thư mục webapps của Tomcat
COPY target/PersonalProject-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

# Expose port 8080
EXPOSE 8080

# Khởi động Tomcat server
CMD ["catalina.sh", "run"]
