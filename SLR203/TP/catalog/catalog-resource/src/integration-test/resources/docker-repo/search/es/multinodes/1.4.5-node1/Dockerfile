######################################################
## build the container
##  docker build  --rm=true -t es:1.4.5-node1 .
######################################################


######################################################
## run the built container
## docker run  -t  es:1.4.5 /bin/bash
## docker run  -t -v  /c/Users/ZCadi/docker/es/data-multinodes:/data  -p 10200:10200 -p 10300:10300 --name es-node1 es:1.4.5-node1

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
RUN apt-get install oracle-java7-installer -y



############### ES  ###############
ENV ES_PKG_NAME elasticsearch-1.4.5

# Install Elasticsearch.
RUN \
  cd / && \
  wget https://download.elasticsearch.org/elasticsearch/elasticsearch/$ES_PKG_NAME.tar.gz && \
  tar xvzf $ES_PKG_NAME.tar.gz && \
  rm -f $ES_PKG_NAME.tar.gz && \
  mv /$ES_PKG_NAME /elasticsearch

# Define mountable directories.
VOLUME ["/data"]

# Mount elasticsearch.yml config
ADD config/elasticsearch.yml /elasticsearch/config/elasticsearch.yml

# Define working directory.
WORKDIR /data


# Define default command.
CMD ["/elasticsearch/bin/elasticsearch"]

# Expose ports.
#   - 10200: HTTP
#   - 10300: transport
EXPOSE 10200
EXPOSE 10300
