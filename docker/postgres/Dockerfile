#FROM postgres:15.1-alpine
#
#RUN apt-get update; \
#		apt-get install -y apt-utils vim zsh curl git locales tzdata; \
#		zsh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
#
## LOCALE 설정
#RUN localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
#ENV LANG en_US.utf8
#
## localtime 에 링크할 시간대를 링크.
#RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
#
## 원하는 시간대 링크후 아래 수행시, /etc/timezone 파일변경됨.
#RUN dpkg-reconfigure -f noninteractive tzdata
#
#CMD ["apt-get", "install", "sudo"]
FROM 8.0.32-debian

RUN apt-get update

RUN sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/g" postgresql.conf
RUN echo "host all all 0.0.0.0/0 password" >> pg_hba.conf

EXPOSE 5432

VOLUME ["/var/lib/postgresql"]

ADD data/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT /entrypoint.sh