package fr.xebia.xke.android.weather.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.api.forecast.DailyWeatherDataPoint;
import fr.xebia.xke.android.weather.api.forecast.HourlyWeatherDataPoint;
import fr.xebia.xke.android.weather.api.forecast.WeatherForecast;
import fr.xebia.xke.android.weather.utils.ResourceUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mounirboudraa on 04/06/13.
 */
@EViewGroup(R.layout.forecast_welcome_header)
public class ForecastHeaderView extends LinearLayout {


    @ViewById(R.id.forecast_current_temperature)
    TextView temperatureTextView;

    @ViewById(R.id.forecast_current_weather_icon)
    ImageView weatherIcon;

    @ViewById(R.id.forecast_current_wind_icon)
    ImageView windIcon;

    @ViewById(R.id.forecast_welcome_dewpoint_value)
    TextView dewPointTextView;

    @ViewById(R.id.forecast_welcome_humidity_value)
    TextView humidityTextView;

    @ViewById(R.id.forecast_welcome_pressure_value)
    TextView pressureTextView;

    @ViewById(R.id.forecast_welcome_feltTemperature_value)
    TextView feltTemperatureTextView;

    @ViewById(R.id.forecast_current_wind_speed)
    TextView windSpeed;

    public ForecastHeaderView(Context context) {
        super(context);
    }


    public void bind(WeatherForecast forecast) {

        DailyWeatherDataPoint todayData = forecast.getDaily().getData().get(0);
        final double todayTempMin = todayData.getTemperatureMin();
        final double todayTempMax = todayData.getTemperatureMax();

        Calendar calendar = Calendar.getInstance();
        final long sunriseTimeInMillis = todayData.getSunriseTime();
        final long sunsetTimeInMillis = todayData.getSunsetTime();

        calendar.setTimeInMillis(sunriseTimeInMillis);
        final Date sunriseTime = calendar.getTime();

        calendar.setTimeInMillis(sunsetTimeInMillis);
        final Date sunsetTime = calendar.getTime();

        HourlyWeatherDataPoint data = forecast.getCurrently();

        temperatureTextView.setText(String.valueOf(Math.round(data.getTemperature())) + "°C");
        dewPointTextView.setText(String.valueOf(Math.round(data.getDewPoint())) + "°C");
        humidityTextView.setText(String.valueOf(Math.round(data.getHumidity() * 100)) + "%");
        pressureTextView.setText(String.valueOf(Math.round(data.getPressure())) + " hPa");
        feltTemperatureTextView.setText(String.valueOf(Math.round(calculateFeltTemperature(data.getTemperature(), forecast
                .getCurrently()
                .getWindSpeed()))) + "°C");
        windSpeed.setText(String.valueOf(Math.round(data.getWindSpeed())) + " km/h");
        Bitmap windIconBmp = ResourceUtils.rotateDrawable(getContext(), R.drawable.wind_arrow, data.getWindBearing());
        windIcon.setImageBitmap(windIconBmp);
        weatherIcon.setImageResource(ResourceUtils.getResourceIdentifierByName(getContext(), ResourceUtils.ResourceType.DRAWABLE, data
                .getIcon()));

    }

    private double calculateFeltTemperature(double temperature, double windSpeed) {
        final double coeff = Math.pow(windSpeed, 0.16);
        final double result = 13.12 + 0.6215 * temperature - 11.37 * coeff + 0.3965 * temperature * coeff;
        return Math.round(result);
    }
}
