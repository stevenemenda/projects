######################################################
## build the container
##  docker build  --rm=true -t zouheir/elasticsearch:2.0.0 .
######################################################


######################################################
## run the built container
## docker run -i -t  zouheir/elasticsearch:2.0.0 /bin/bash
## docker run -i -t -v  /Users/zouheir/docker-data/es/data-mononode:/data  -p 8200:8200 -p 9300:9300 elasticsearch:2.0.0
## docker run  -t -v /Users/zouheir/docker-data/es/data-mononode-2.0.0:/data  -p 8200:8200 -p 9300:9300 --name elasticsearch zouheir/elasticsearch:2.0.0
## docker run  -t   -p 8200:8200 -p 9300:9300 --name elasticsearch elasticsearch:2.0.0
######################################################

############### UBUNTU  ###############
# Base image with ubuntu
FROM ubuntu:14.04

RUN apt-get update

RUN apt-get install curl -y
RUN apt-get install wget -y
RUN apt-get install vim  -y --force-yes 

############### JAVA  ###############
RUN apt-get update
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN apt-get install oracle-java8-installer -y



############### ES  ###############
# grab gosu for easy step-down from root
RUN gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4
RUN arch="$(dpkg --print-architecture)" \
	&& set -x \
	&& curl -o /usr/local/bin/gosu -fSL "https://github.com/tianon/gosu/releases/download/1.3/gosu-$arch" \
	&& curl -o /usr/local/bin/gosu.asc -fSL "https://github.com/tianon/gosu/releases/download/1.3/gosu-$arch.asc" \
	&& gpg --verify /usr/local/bin/gosu.asc \
	&& rm /usr/local/bin/gosu.asc \
	&& chmod +x /usr/local/bin/gosu

RUN apt-key adv --keyserver ha.pool.sks-keyservers.net --recv-keys 46095ACC8548582C1A2699A9D27D666CD88E42B4

ENV ELASTICSEARCH_MAJOR 2.0
ENV ELASTICSEARCH_VERSION 2.0.0
ENV ELASTICSEARCH_REPO_BASE http://packages.elasticsearch.org/elasticsearch/2.x/debian

RUN echo "deb $ELASTICSEARCH_REPO_BASE stable main" > /etc/apt/sources.list.d/elasticsearch.list

RUN set -x \
	&& apt-get update \
	&& apt-get install -y --no-install-recommends elasticsearch=$ELASTICSEARCH_VERSION \
	&& rm -rf /var/lib/apt/lists/*

ENV PATH /usr/share/elasticsearch/bin:$PATH

RUN set -ex \
	&& for path in \
		/usr/share/elasticsearch/data \
		/usr/share/elasticsearch/logs \
		/usr/share/elasticsearch/config \
		/usr/share/elasticsearch/config/scripts \
	; do \
		mkdir -p "$path"; \
		chown -R elasticsearch:elasticsearch "$path"; \
	done

COPY config /usr/share/elasticsearch/config

VOLUME /usr/share/elasticsearch/data

COPY docker-entrypoint.sh /

ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 8200 9300

CMD ["elasticsearch"]