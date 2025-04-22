#!/bin/bash

set -e

# Use your actual WSL path here:
BASE_PATH="/mnt/c/Users/Kiu-Student/Desktop/devops-midterm/devops-midterm"
BUILD_DIR="$BASE_PATH/build"
PROD_A="$BASE_PATH/production_a"
PROD_B="$BASE_PATH/production_b"
LIVE_LINK="$BASE_PATH/live"

if [ -L "$LIVE_LINK" ]; then
    CURRENT=$(readlink "$LIVE_LINK")
else
    CURRENT=""
fi

if [[ "$CURRENT" == "$PROD_A" ]]; then
    TARGET="$PROD_B"
else
    TARGET="$PROD_A"
fi

echo "Deploying to: $TARGET"

mkdir -p "$TARGET"

rm -rf "$TARGET"/*

cp -r "$BUILD_DIR"/* "$TARGET"/


rm -f "$LIVE_LINK"
ln -s "$TARGET" "$LIVE_LINK"

echo "Deployment complete. Live now points to: $TARGET"
