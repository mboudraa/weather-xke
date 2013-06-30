package fr.xebia.xke.android.weather.request.response;

import fr.xebia.xke.android.weather.api.forecast.WeatherForecast;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;

/**
 * Created by mounirboudraa on 31/05/13.
 */
public class WeatherForecastResponse {

    private WeatherLocation weatherLocation;
    private WeatherForecast forecast;


    public WeatherLocation getWeatherLocation() {
        return weatherLocation;
    }

    public void setWeatherLocation(WeatherLocation weatherLocation) {
        this.weatherLocation = weatherLocation;
    }

    public WeatherForecast getForecast() {
        return forecast;
    }

    public void setForecast(WeatherForecast forecast) {
        this.forecast = forecast;
    }




}
