#!/bin/bash

PROD_A="/mnt/c/Users/Kiu-Student/Desktop/devops-midterm/devops-midterm/production_a"
PROD_B="/mnt/c/Users/Kiu-Student/Desktop/devops-midterm/devops-midterm/production_b"
LIVE_LINK="/mnt/c/Users/Kiu-Student/Desktop/devops-midterm/devops-midterm/live"


if [ -L "$LIVE_LINK" ]; then
    CURRENT=$(readlink "$LIVE_LINK")
    if [[ "$CURRENT" == "$PROD_A" ]]; then
        ln -sfn "$PROD_B" "$LIVE_LINK"
        echo "Rolled back to: $PROD_B"
    else
        ln -sfn "$PROD_A" "$LIVE_LINK"
        echo "Rolled back to: $PROD_A"
    fi
else
    echo "Live symlink not found. Cannot roll back."
fi
