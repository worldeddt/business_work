#FROM ubuntu:14.04
#
#ENV DEBIAN_FRONTEND noninteractive
#
#RUN apt-get update
#RUN echo "mysql-server mysql-server/root_password password" | debconf-set-selections
#RUN echo "mysql-server mysql-server/root_password_again password" | debconf-set-selections
#RUN apt-get install -y mysql-server
#VOLUME ["/var/lib/mysql"]
#VOLUME ["/var/lib/mysql"]
#
#WORKDIR /etc/mysql
#RUN sed -i "s/127.0.0.1/0.0.0.0/g" my.cnf
#
#ADD entrypoint.sh /entrypoint.sh
#RUN chmod +x /entrypoint.sh
#EXPOSE 3306
#
#ENTRYPOINT /entrypoint.sh

FROM mysql:8

EXPOSE 3306

ENV MYSQL_USER = eddy
ENV MYSQL_ROOT_PASSWORD=eddy


#ENV MYSQL_DATABASE=eddy