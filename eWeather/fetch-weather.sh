#!/bin/bash

echo "=========================================="
echo "Fetching Weather Data"
echo "=========================================="
echo ""

# Check if Derby is running
if ! lsof -i :1527 > /dev/null 2>&1; then
    echo "❌ ERROR: Derby server is not running!"
    echo "Please start it first with: ./start-derby.sh"
    exit 1
fi

echo "✓ Derby server is running"
echo ""
echo "Fetching weather data from OpenWeatherMap..."
echo ""

# Create a simple Java program to fetch weather
cat > /tmp/FetchWeather.java << 'EOF'
import com.mycompany.eweather.TempUtils;

public class FetchWeather {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to OpenWeatherMap API...");
            TempUtils utils = new TempUtils();
            utils.refreshWeatherForAllCities();
            System.out.println("✅ Weather data fetched successfully!");
            System.out.println("");
            System.out.println("You can now view the weather in the eWeather app.");
        } catch (Exception e) {
            System.err.println("❌ Error fetching weather data:");
            e.printStackTrace();
        }
    }
}
EOF

# Compile and run
javac -cp target/eWeather-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/FetchWeather.java 2>/dev/null
java -cp target/eWeather-1.0-SNAPSHOT-jar-with-dependencies.jar:/tmp FetchWeather

rm -f /tmp/FetchWeather.java /tmp/FetchWeather.class

echo ""
echo "=========================================="
echo "Done!"
echo "=========================================="
