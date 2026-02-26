#!/bin/bash

echo "=========================================="
echo "eWeather Application Launcher"
echo "=========================================="
echo ""

# Set environment to prevent Mac resource fork files
export COPYFILE_DISABLE=1

# Navigate to the eWeather directory
cd "$(dirname "$0")"

echo "Cleaning Mac resource fork files..."
find . -name "._*" -type f -delete 2>/dev/null

echo "Building project with all dependencies..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "ERROR: Build failed!"
    exit 1
fi

echo "Removing any remaining resource fork files..."
find target -name "._*" -type f -delete 2>/dev/null

echo ""
echo "Starting eWeather application..."
echo "=========================================="
echo ""

# Run the fat JAR with all dependencies included
java -jar target/eWeather-1.0-SNAPSHOT-jar-with-dependencies.jar
