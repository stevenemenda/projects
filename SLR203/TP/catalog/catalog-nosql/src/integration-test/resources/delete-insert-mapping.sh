
HOST_DOCKER=192.168.99.100
# HOST_DOCKER=localhost
PORT_DOCKER=8200

HOST=$HOST_DOCKER
PORT=$PORT_DOCKER

INDEX=catalog
VERSION=1
TYPE=phone

HOST_PORT="http://${HOST}:${PORT}"
echo "*** ${HOST_PORT}"

curl -X PUT "${HOST_PORT}/${INDEX}_v${VERSION}" -d '{
	"settings" : {
		"index" : {
			"number_of_shards" : 1,
			"number_of_replicas" : 0
			"number_of_replicas" : 0
		}
	}
}'




# DELETE INDEX
		echo ${HOST_PORT}/${INDEX}
		echo "*** DELETE INDEX  ${INDEX}"
		curl -X DELETE "${HOST_PORT}/${INDEX}"
		echo "*** INDEX  ${INDEX} DELETED"

		
# DELETE MAPPING
		echo "*** DELETE MAPPING ${HOST_PORT}/${INDEX}_v${VERSION}/_mapping/${TYPE}"
		curl -X DELETE "${HOST_PORT}/${INDEX}_v${VERSION}/_mapping/${TYPE}"
		echo "*** MAPPING ${HOST_PORT}/${INDEX}_v${VERSION}/_mapping/${TYPE} DELETED "

# CREATE INDEX VERSION vn 
## DEV MODE NO REPLICAS 
## DEV MODE 0 SHARDS
		echo "*** CREATE INDEX ${HOST_PORT}/${INDEX}_v${VERSION}"
		curl -X PUT "${HOST_PORT}/${INDEX}_v${VERSION}" -d '{
		    "settings" : {
		        "index" : {
		            "number_of_shards" : 1,
		            "number_of_replicas" : 0
		        }
		    }
		}'
		echo "*** INDEX ${HOST_PORT}/${INDEX}_v${VERSION} CREATED"
		
		
# CREATE ALIAS POINTING TO INDEX vn 
		echo "CREATE ALIAS POINTING TO INDEX vn  ${HOST_PORT}/${INDEX}_v${VERSION}/_alias/${INDEX}"
		curl -XPUT "${HOST_PORT}/${INDEX}_v${VERSION}/_alias/${INDEX}"
		echo "CREATE ALIAS POINTING TO INDEX vn  ${HOST_PORT}/${INDEX}_v${VERSION}/_alias/${INDEX} DONE"
		
# Check which index the alias points to:
		echo "Check which index the alias points to: ${HOST_PORT}/_alias/${INDEX}?pretty=true"
		curl -X GET  "${HOST_PORT}/_alias/${INDEX}?pretty=true"
		
# Control which aliases point to the index:
		echo "Control which aliases point to the index: ${HOST_PORT}/${INDEX}_v${VERSION}/_alias/*?pretty=true"
		curl -X GET  "${HOST_PORT}/${INDEX}_v${VERSION}/_alias/*?pretty=true"
		echo "Check Done"






# CREATE FULL MAPPING Vn		
curl -X PUT "${HOST_PORT}/${INDEX}_v${VERSION}/_mapping/${TYPE}" -d '		
{
  "phone": {
    "properties": {
      "age": {
        "type": "integer",
        "index": "not_analyzed",
        "include_in_all": false
      },
      "name": {
        "type": "string",
        "include_in_all": true
      },
      "snippet": {
        "type": "string",
        "include_in_all": true
      },
      "imageUrl": {
        "type": "string",
        "index": "not_analyzed",
        "include_in_all": false
      }
    }
  }
}
'
