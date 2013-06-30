package fr.xebia.xke.android.weather.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.googlecode.androidannotations.annotations.*;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.adapter.CityListAdapter;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;

import java.util.ArrayList;

/**
 * User: mounirboudraa
 * Date: 24/05/13
 * Time: 13:11
 */
@EFragment(R.layout.city_search)
public class SelectPlaceFragment extends BaseFragment {

    public static final String TAG = SelectPlaceFragment.class.getName();

    @ViewById(R.id.city_response_listview)
    ListView citiesResponseListView;

    @Bean
    CityListAdapter placeAdapter;

    @InstanceState
    ArrayList<WeatherLocation> mWeatherLocations = new ArrayList<WeatherLocation>();

    @AfterViews
    protected void afterViewCreated() {
        citiesResponseListView.setAdapter(placeAdapter);

        citiesResponseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeatherLocation selectedWeatherLocation = mWeatherLocations.get(position);
                mActivity.showForecastWelcomeFragment(selectedWeatherLocation);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(mWeatherLocations);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    public void updateUI(ArrayList<WeatherLocation> weatherLocations) {
        mWeatherLocations = weatherLocations;
        placeAdapter.setData(weatherLocations);
    }

}
