FROM mysql:5.7

ENV MYSQL_DATABASE=github \
    MYSQL_ROOT_PASSWORD=root

ADD ./support/db-init.sql /docker-entrypoint-initdb.d/

EXPOSE 3306
