FROM nginx:1.22.1
MAINTAINER Eddy

EXPOSE 8090

COPY ./nginx.conf /etc/nginx/nginx.conf
COPY ./default.conf /etc/nginx/conf.d/default.conf

RUN mvnw clean package

ENTRYPOINT ["nginx", "-g", "daemon off;"]