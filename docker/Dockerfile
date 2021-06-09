# docker build -t postgres:no_pwd .
# escape=`

FROM postgres
ENV POSTGRES_HOST_AUTH_METHOD=trust
COPY init.sql /docker-entrypoint-initdb.d/
