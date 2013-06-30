package fr.xebia.xke.android.weather.request;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import fr.xebia.xke.android.weather.api.forecast.WeatherForecast;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.request.response.WeatherForecastResponse;


/**
 * Created by mounirboudraa on 27/05/13.
 */
public class ForecastRequest extends SpringAndroidSpiceRequest<WeatherForecastResponse> {

    private static final String REQUEST_QUERY_BASIC = "https://api.forecast.io/forecast";

    private final WeatherLocation weatherLocation;
    private final String apiKey;

    public ForecastRequest(WeatherLocation weatherLocation, String apiKey) {
        super(WeatherForecastResponse.class);
        this.weatherLocation = weatherLocation;
        this.apiKey = apiKey;
    }

    @Override
    public WeatherForecastResponse loadDataFromNetwork() throws Exception {
        WeatherForecast forecast =       getRestTemplate().getForObject(getUrlQuery(),WeatherForecast.class);
        WeatherForecastResponse response = new WeatherForecastResponse();
        response.setForecast(forecast);
        response.setWeatherLocation(weatherLocation);
        return response;
    }


    private String getUrlQuery() {

        StringBuilder queryBuffer = new StringBuilder();
        queryBuffer.append(REQUEST_QUERY_BASIC)
                   .append("/")
                   .append(apiKey)
                   .append("/");
        if (weatherLocation != null) {
            queryBuffer.append(weatherLocation.getLatitude())
                       .append(",")
                       .append(weatherLocation.getLongitude());
        }
        queryBuffer.append("?units=ca&exclude=flags&extend=hourly");

        return queryBuffer.toString();
    }
}
