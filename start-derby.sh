#!/bin/bash

echo "=========================================="
echo "Derby Database Server Starter"
echo "=========================================="
echo ""

# Get the Derby jars from Maven repository
DERBY_JAR="$HOME/.m2/repository/org/apache/derby/derby/10.15.2.0/derby-10.15.2.0.jar"
DERBYNET_JAR="$HOME/.m2/repository/org/apache/derby/derbynet/10.15.2.0/derbynet-10.15.2.0.jar"
DERBYSHARED_JAR="$HOME/.m2/repository/org/apache/derby/derbyshared/10.15.2.0/derbyshared-10.15.2.0.jar"
DERBYTOOLS_JAR="$HOME/.m2/repository/org/apache/derby/derbytools/10.15.2.0/derbytools-10.15.2.0.jar"

# Check if Derby jars exist
if [ ! -f "$DERBY_JAR" ] || [ ! -f "$DERBYNET_JAR" ] || [ ! -f "$DERBYTOOLS_JAR" ]; then
    echo "Derby jars not found. Downloading..."
    mvn dependency:get -Dartifact=org.apache.derby:derby:10.15.2.0 -q
    mvn dependency:get -Dartifact=org.apache.derby:derbynet:10.15.2.0 -q
    mvn dependency:get -Dartifact=org.apache.derby:derbyshared:10.15.2.0 -q
    mvn dependency:get -Dartifact=org.apache.derby:derbytools:10.15.2.0 -q
fi

# Set the database directory - Derby will look for databases here
export DERBY_SYSTEM_HOME="$(pwd)"

echo "Derby system home: $DERBY_SYSTEM_HOME"
echo "Database will be created/accessed in: $DERBY_SYSTEM_HOME/weather"
echo ""
echo "Starting Derby Network Server on port 1527..."
echo "Press Ctrl+C to stop the server"
echo ""
echo "=========================================="
echo ""

# Start Derby network server with proper classpath
java -Dderby.system.home="$DERBY_SYSTEM_HOME" -cp "$DERBY_JAR:$DERBYNET_JAR:$DERBYSHARED_JAR:$DERBYTOOLS_JAR" org.apache.derby.drda.NetworkServerControl start -h 0.0.0.0 -p 1527 -noSecurityManager

echo ""
echo "Derby server stopped."
