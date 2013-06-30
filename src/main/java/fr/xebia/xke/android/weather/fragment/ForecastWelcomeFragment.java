package fr.xebia.xke.android.weather.fragment;

import android.widget.ListView;
import com.googlecode.androidannotations.annotations.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
    PullToRefreshListView mForecastRefreshListView;

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
        mForecastRefreshListView.onRefreshComplete();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @AfterViews
    protected void onViewCreated() {

        mForecastHeader = ForecastHeaderView_.build(getActivity());
        mForecastRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        mForecastRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                mActivity.loadForecast(mCurrentLocation);
            }
        });

        ListView original = mForecastRefreshListView.getRefreshableView();
        original.addHeaderView(mForecastHeader, null, false);
        original.setHeaderDividersEnabled(true);
        original.setAdapter(mForecastListViewAdapter);
        if (mWeatherForecast != null) {
            updateUI(mWeatherForecast, mCurrentLocation);
        }
    }

}
