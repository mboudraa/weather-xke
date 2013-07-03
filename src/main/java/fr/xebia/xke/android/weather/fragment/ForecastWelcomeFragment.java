package fr.xebia.xke.android.weather.fragment;

import android.widget.ListView;
import com.googlecode.androidannotations.annotations.*;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.adapter.ForecastWeekListAdapter;
import fr.xebia.xke.android.weather.api.forecast.DailyWeatherDataPoint;
import fr.xebia.xke.android.weather.api.forecast.WeatherForecast;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.view.ForecastHeaderView;
import fr.xebia.xke.android.weather.view.ForecastHeaderView_;

import java.util.ArrayList;


/**
 * Created by mounirboudraa on 27/05/13.
 */
@EFragment(R.layout.forecast_welcome)
public class ForecastWelcomeFragment extends BaseFragment {

    public static final String TAG = ForecastWelcomeFragment.class.getName();

    @ViewById(R.id.forecast_welcome_week_listview)
    ListView mForecastRefreshListView;

    @Bean
    ForecastWeekListAdapter mForecastListViewAdapter;

    @InstanceState
    WeatherForecast mWeatherForecast;

    @InstanceState
    ArrayList<DailyWeatherDataPoint> dailyWeatherList = new ArrayList<DailyWeatherDataPoint>();

    @InstanceState
    WeatherLocation mCurrentLocation;

    private ForecastHeaderView mForecastHeader;

//    @UiThread
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

        mForecastHeader = ForecastHeaderView_.build(getActivity());

        mForecastRefreshListView.addHeaderView(mForecastHeader, null, false);
        mForecastRefreshListView.setHeaderDividersEnabled(true);
        mForecastRefreshListView.setAdapter(mForecastListViewAdapter);
        if (mWeatherForecast != null) {
            updateUI(mWeatherForecast, mCurrentLocation);
        }
    }

}
