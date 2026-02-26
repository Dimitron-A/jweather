#!/bin/bash

echo "Testing OpenWeatherMap API..."
echo ""

# Read the API key from TempUtils.java
API_KEY=$(grep 'private String key = ' src/main/java/com/mycompany/eweather/TempUtils.java | sed 's/.*"\(.*\)".*/\1/')

echo "API Key found: ${API_KEY:0:10}..."
echo ""

# Test with Athens (city ID: 264371)
echo "Testing API call for Athens..."
RESPONSE=$(curl -s "https://api.openweathermap.org/data/2.5/group?id=264371&units=metric&appid=$API_KEY")

echo ""
echo "Response:"
echo "$RESPONSE" | head -c 500
echo ""
echo ""

# Check if response contains error
if echo "$RESPONSE" | grep -q '"cod":401'; then
    echo "❌ ERROR: Invalid API key!"
    echo "Please get a new API key from https://openweathermap.org/"
elif echo "$RESPONSE" | grep -q '"cod":"200"'; then
    echo "✅ SUCCESS: API key is working!"
    echo "Temperature in Athens: $(echo "$RESPONSE" | grep -o '"temp":[0-9.]*' | head -1 | cut -d: -f2)°C"
else
    echo "⚠️  Unexpected response. Check the output above."
fi
