# eWeather - Greek Weather Application

A Java desktop application that displays current weather and forecasts for Greek cities using the OpenWeatherMap API.

## Features

- 🌤️ Current weather for 5 Greek cities (Athens, Thessaloniki, Patras, Heraklion, Larissa)
- 📊 Weather forecasts (1-day and 5-day predictions)
- 📈 Temperature statistics
- 💾 Local Derby database for storing weather data
- 🌐 Real-time data from OpenWeatherMap API

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- OpenWeatherMap API key (free account)

## Setup

### 1. Get OpenWeatherMap API Key

1. Go to [OpenWeatherMap](https://openweathermap.org/)
2. Sign up for a free account
3. Go to "API keys" section
4. Copy your API key

### 2. Configure API Key

Edit `eWeather/src/main/java/com/mycompany/eweather/TempUtils.java`:

```java
private String key = "YOUR_API_KEY_HERE";
```

### 3. Build the Project

```bash
cd eWeather
mvn clean package -DskipTests
```

## Running the Application

You need TWO terminal windows:

### Terminal 1: Start Derby Database Server

```bash
./start-derby.sh
```

Keep this terminal open while using the app.

### Terminal 2: Run the Application

```bash
cd eWeather
./run-app.sh
```

Or manually:

```bash
cd eWeather
java -jar target/eWeather-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Usage

1. **Fetch Weather Data**: Click "Ανανέωση Καιρού" (Refresh Weather) to download current weather
2. **View Current Weather**: Select cities and click "Καιρός Τώρα" (Weather Now)
3. **View Forecasts**: Go to "Πρόβλεψη καιρού" tab and select forecast options

## Project Structure

```
.
├── eWeather/              # Main Java application
│   ├── src/              # Source code
│   ├── pom.xml           # Maven configuration
│   ├── run-app.sh        # Application launcher
│   └── test-api.sh       # API key tester
├── start-derby.sh        # Derby server launcher
└── ΟΔΗΓΙΕΣ.md           # Greek instructions
```

## Technologies Used

- Java 11
- Hibernate 5.6.15
- Apache Derby 10.15.2
- Jackson 2.15.3
- OpenWeatherMap API
- Java Swing (GUI)

## Database

The application uses Apache Derby as an embedded database to store:
- City information
- Weather snapshots
- Weather forecasts

The database is automatically created on first run.

## Troubleshooting

### App closes immediately
- Make sure Derby server is running (`./start-derby.sh`)
- Check that the API key is valid and active

### No weather data showing
- Click "Ανανέωση Καιρού" to fetch data from OpenWeatherMap
- Verify your API key is active (may take 10-15 minutes after creation)
- Test API key: `cd eWeather && ./test-api.sh`

### Build fails
- Ensure Java 11+ is installed: `java -version`
- Ensure Maven is installed: `mvn -version`
- Clean and rebuild: `mvn clean package -DskipTests`

## License

This project is for educational purposes.

## Author

Developed as part of IEK coursework.
