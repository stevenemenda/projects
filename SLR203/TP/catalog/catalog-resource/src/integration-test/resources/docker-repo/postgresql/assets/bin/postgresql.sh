#!/usr/bin/env bash
create_user () {
    echo "Waiting for PostgreSQL to start"

   /etc/init.d/postgresql start

    # We sleep here for 2 seconds to allow clean output, and speration from postgres startup messages
    sleep 5
    echo "Below are your configured options."
    echo -e "================\nUSER: $USER\nPASSWORD: $PASSWORD\nSCHEMA: $SCHEMA\nENCODING: $ENCODING\nPOSTGIS: $POSTGIS\n================"


    psql --command "CREATE USER pguser WITH SUPERUSER PASSWORD 'pgpassword';" 
    psql --command "CREATE DATABASE catalogdb WITH OWNER pguser ENCODING 'UTF8' TEMPLATE template0;"


    psql -d catalogdb --command "CREATE SCHEMA IF NOT EXISTS catalogscm AUTHORIZATION pguser;"	
	
}
create_user &

exec /usr/lib/postgresql/9.3/bin/postgres -D /var/lib/postgresql/9.3/main -c config_file=/etc/postgresql/9.3/main/postgresql.conf
