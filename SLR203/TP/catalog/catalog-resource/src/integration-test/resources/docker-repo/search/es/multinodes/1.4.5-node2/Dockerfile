######################################################
## build the container
##  docker build  --rm=true -t es:1.4.5-node2 .
######################################################


######################################################
## run the built container
## docker run  -t -v  /c/Users/ZCadi/es/data-multinodes:/data  -p 20200:20200 -p 20300:20300 --name es-node2 es:1.4.5-node2
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
#   - 20200: HTTP
#   - 20300: transport
EXPOSE 20200
EXPOSE 20300
