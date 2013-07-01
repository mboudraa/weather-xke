package fr.xebia.xke.android.weather.fragment;

import android.widget.ListView;
import com.googlecode.androidannotations.annotations.AfterViews;
import fr.xebia.xke.android.weather.adapter.ForecastWeekListAdapter;
import fr.xebia.xke.android.weather.api.forecast.DailyWeatherDataPoint;
import fr.xebia.xke.android.weather.api.forecast.WeatherForecast;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.view.ForecastHeaderView;

import java.util.ArrayList;


/**
 * Created by mounirboudraa on 27/05/13.
 */

//TODO Bind with layout R.layout.forecast_welcome
//TODO Use the corrects annotation to inject Fields
public class ForecastWelcomeFragment extends BaseFragment {

    public static final String TAG = ForecastWelcomeFragment.class.getName();

    ListView mForecastListView;

    ForecastWeekListAdapter mForecastListViewAdapter;

    WeatherForecast mWeatherForecast;

    ArrayList<DailyWeatherDataPoint> dailyWeatherList = new ArrayList<DailyWeatherDataPoint>();

    WeatherLocation mCurrentLocation;

    private ForecastHeaderView mForecastHeader;

    public void updateUI(WeatherForecast forecast, WeatherLocation location) {


        mWeatherForecast = forecast;
        mCurrentLocation = location;
        dailyWeatherList = new ArrayList<DailyWeatherDataPoint>(forecast.getDaily().getData());

        mForecastHeader.bind(forecast);
        mForecastListViewAdapter.setData(dailyWeatherList);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @AfterViews
    protected void onViewCreated() {

        //TODO init ListView Header
        //TODO Add LIstView Header to the listView
        //TODO Set listView Adapter

        mForecastListView.setHeaderDividersEnabled(true);
        if (mWeatherForecast != null) {
            updateUI(mWeatherForecast, mCurrentLocation);
        }
    }

}
