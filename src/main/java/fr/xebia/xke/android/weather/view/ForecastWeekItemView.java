package fr.xebia.xke.android.weather.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.api.forecast.DailyWeatherDataPoint;
import fr.xebia.xke.android.weather.utils.ResourceUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * User: mounirboudraa
 * Date: 23/05/13
 * Time: 17:16
 */
@EViewGroup(R.layout.forecast_welcome_week_content)
public class ForecastWeekItemView extends LinearLayout {


    @ViewById(R.id.forecast_week_weather_icon)
    ImageView weatherIcon;

    @ViewById(R.id.forecast_week_tempmin)
    TextView temperatureMin;


    @ViewById(R.id.forecast_week_tempmax)
    TextView temperatureMax;

    @ViewById(R.id.forecast_week_day)
    TextView dayOfWeek;

    @ViewById(R.id.forecast_week_date)
    TextView date;

    @ViewById(R.id.forecast_week_cloudCover)
    TextView cloudCover;


    @ViewById(R.id.forecast_week_windSpeed_icon)
    ImageView windIcon;

    @ViewById(R.id.forecast_week_windSpeed)
    TextView windSpeed;

    public ForecastWeekItemView(Context context) {
        super(context);
    }


    public void bind(DailyWeatherDataPoint data) {
        weatherIcon.setImageResource(ResourceUtils.getResourceIdentifierByName(getContext(), ResourceUtils.ResourceType.DRAWABLE, data
                .getIcon()+"_mini"));

        temperatureMin.setText(String.valueOf(Math.round(data.getTemperatureMin()))+"°C");
        temperatureMax.setText(String.valueOf(Math.round(data.getTemperatureMax()))+"°C");

        final long timeInMillis = data.getTime()*1000;
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timeInMillis);

        if(DateUtils.isToday(timeInMillis)){
            dayOfWeek.setText(getResources().getText(R.string.today_short));
        }else {
            dayOfWeek.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        }

        String fomattedDate = DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault()).format(calendar.getTime());
        date.setText(fomattedDate);

        cloudCover.setText(String.valueOf(Math.round(data.getCloudCover()*100))+"%");
        windSpeed.setText(String.valueOf(Math.round(data.getWindSpeed())));
        BitmapDrawable drawable = new BitmapDrawable(getResources(),ResourceUtils.rotateDrawable(getContext(), R.drawable.wind_arrow, data
                .getWindBearing()));
        windIcon.setImageDrawable(drawable);
    }
}
