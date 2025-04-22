#!/bin/bash

URL="http://localhost:8080/students/all"
LOG_FILE="health.log"
TIMESTAMP=$(date +"%Y-%m-%d %H:%M:%S")

HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" $URL)

if [ "$HTTP_STATUS" -eq 200 ]; then
    echo "$TIMESTAMP - SUCCESS: App is running (HTTP $HTTP_STATUS)" >> $LOG_FILE
else
    echo "$TIMESTAMP - ERROR: App is NOT running (HTTP $HTTP_STATUS)" >> $LOG_FILE
fi
