#!/bin/bash

./healthcheck.sh

LAST_LINE=$(tail -n 1 health.log)

if echo "$LAST_LINE" | grep -q "ERROR"; then
    echo "[AUTO-ROLLBACK] Health check failed: $LAST_LINE"
    echo "[AUTO-ROLLBACK] Rolling back to previous version..."
    ./rollback.sh
else
    echo "[AUTO-ROLLBACK] Health check passed: $LAST_LINE"
    echo "[AUTO-ROLLBACK] No rollback needed."
fi
