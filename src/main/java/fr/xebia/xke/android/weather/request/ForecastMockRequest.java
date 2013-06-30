package fr.xebia.xke.android.weather.request;

import android.content.Context;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.api.forecast.WeatherForecast;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.request.response.WeatherForecastResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


/**
 * Created by mounirboudraa on 27/05/13.
 */
public class ForecastMockRequest extends ForecastRequest {

    private static final String ENCODING = "UTF-8";
    private Context mContext;

    private WeatherLocation mLocation;

    public ForecastMockRequest(Context context, WeatherLocation location) {
        super(null,null);
        mContext = context;
        mLocation = location;
    }

    @Override
    public WeatherForecastResponse loadDataFromNetwork() throws Exception {

        InputStream is = mContext.getResources().openRawResource(R.raw.forecast_mock);

        Reader reader = new BufferedReader(new InputStreamReader(is, ENCODING));

        JsonParser jsonParser = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            jsonParser = factory.createParser(reader);
            WeatherForecast forecast = jsonParser.readValueAs(WeatherForecast.class);
            WeatherForecastResponse response = new WeatherForecastResponse();
            response.setForecast(forecast);
            response.setWeatherLocation(mLocation);
            return response;
        } finally {
            if (jsonParser != null) {
                jsonParser.close();
            }
            reader.close();
        }

    }

}
