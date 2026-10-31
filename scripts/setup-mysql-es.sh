#!/bin/sh

set -eu

export SQL_PASSWORD="${MYSQL_PWD}"

echo "Starting Temporal MySQL schema setup..."

temporal-sql-tool \
  --plugin mysql8 \
  --ep "$MYSQL_SEEDS" \
  -u "$MYSQL_USER" \
  -p "${DB_PORT:-3306}" \
  --db temporal create

temporal-sql-tool \
  --plugin mysql8 \
  --ep "$MYSQL_SEEDS" \
  -u "$MYSQL_USER" \
  -p "${DB_PORT:-3306}" \
  --db temporal setup-schema -v 0.0

temporal-sql-tool \
  --plugin mysql8 \
  --ep "$MYSQL_SEEDS" \
  -u "$MYSQL_USER" \
  -p "${DB_PORT:-3306}" \
  --db temporal \
  update-schema \
  -d /etc/temporal/schema/mysql/v8/temporal/versioned

echo "Initializing Elasticsearch schema..."

temporal-elasticsearch-tool \
  --ep "$ES_SCHEME://$ES_HOST:$ES_PORT" \
  setup-schema

temporal-elasticsearch-tool \
  --ep "$ES_SCHEME://$ES_HOST:$ES_PORT" \
  create-index \
  --index "$ES_VISIBILITY_INDEX"

echo "Temporal database initialization completed."
