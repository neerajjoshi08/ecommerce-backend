#!/bin/sh

set -eu

NAMESPACE="${DEFAULT_NAMESPACE:-default}"
RETENTION="${DEFAULT_NAMESPACE_RETENTION:-24h}"
ADDRESS="${TEMPORAL_ADDRESS:-temporal:7233}"

echo "Waiting for Temporal server..."

attempt=1
max_attempts=60

until temporal operator cluster health \
  --address "$ADDRESS" >/dev/null 2>&1
do
    if [ "$attempt" -ge "$max_attempts" ]; then
        echo "Temporal server did not become healthy."
        exit 1
    fi

    echo "Waiting for Temporal... ($attempt/$max_attempts)"

    attempt=$((attempt + 1))
    sleep 5
done

echo "Temporal server is healthy."

if temporal operator namespace describe \
  -n "$NAMESPACE" \
  --address "$ADDRESS" >/dev/null 2>&1
then
    echo "Namespace $NAMESPACE already exists."
else
    echo "Creating namespace $NAMESPACE..."

    temporal operator namespace create \
      -n "$NAMESPACE" \
      --retention "$RETENTION" \
      --address "$ADDRESS"
fi

echo "Namespace initialization completed."
