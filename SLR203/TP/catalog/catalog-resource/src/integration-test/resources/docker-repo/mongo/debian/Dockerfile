######################################################
## build the container
## docker ps | grep mong | cut -b1-12
##  docker build  --rm=true -t zouheir/mongo:2.6.11 .
######################################################


######################################################
## run the built container
## docker run -i -t  zouheir/mongo:2.6.11 /bin/bash
## docker run -i -t -v  /Users/zouheir/docker-data/mongo/data-mononode:/data  -p 27017:27017 --name mongo zouheir/mongo:2.6.11
## docker run  -t -v /Users/zouheir/docker-data/mongo/data-mononode:/data/db  -p 27017:27017 --name mongo zouheir/mongo:2.6.11
## ocker run  -t   -p 27017:27017 --name mongo zouheir/mongo:2.6.11
######################################################


FROM debian:wheezy

# add our user and group first to make sure their IDs get assigned consistently, regardless of whatever dependencies get added
RUN groupadd -r mongodb && useradd -r -g mongodb mongodb

RUN apt-get update \
	&& apt-get install -y --no-install-recommends \
		ca-certificates curl \
		numactl \
	&& rm -rf /var/lib/apt/lists/*

# grab gosu for easy step-down from root
RUN gpg --keyserver ha.pool.sks-keyservers.net --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4
RUN curl -o /usr/local/bin/gosu -SL "https://github.com/tianon/gosu/releases/download/1.6/gosu-$(dpkg --print-architecture)" \
	&& curl -o /usr/local/bin/gosu.asc -SL "https://github.com/tianon/gosu/releases/download/1.6/gosu-$(dpkg --print-architecture).asc" \
	&& gpg --verify /usr/local/bin/gosu.asc \
	&& rm /usr/local/bin/gosu.asc \
	&& chmod +x /usr/local/bin/gosu

RUN gpg --keyserver ha.pool.sks-keyservers.net --recv-keys DFFA3DCF326E302C4787673A01C4E7FAAAB2461C

ENV MONGO_VERSION 2.6.11

RUN curl -SL "https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-$MONGO_VERSION.tgz" -o mongo.tgz \
	&& curl -SL "https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-$MONGO_VERSION.tgz.sig" -o mongo.tgz.sig \
	&& gpg --verify mongo.tgz.sig \
	&& tar -xvf mongo.tgz -C /usr/local --strip-components=1 \
	&& rm mongo.tgz*


VOLUME ["/data/db"]
WORKDIR /data

RUN chown mongodb:mongodb /data/db

COPY bin/docker-entrypoint.sh /sbin/entrypoint.sh
RUN chmod 755 /sbin/entrypoint.sh
ENTRYPOINT ["/sbin/entrypoint.sh"]

EXPOSE 27017
CMD ["mongod"]
