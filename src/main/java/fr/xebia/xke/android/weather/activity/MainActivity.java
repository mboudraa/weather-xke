package fr.xebia.xke.android.weather.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.adapter.CityListAdapter;
import fr.xebia.xke.android.weather.api.favorites.FavoritesChangeListener;
import fr.xebia.xke.android.weather.api.favorites.FavoritesManager;
import fr.xebia.xke.android.weather.api.forecast.WeatherForecast;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.api.location.WeatherLocationListener;
import fr.xebia.xke.android.weather.api.location.WeatherLocationManager;
import fr.xebia.xke.android.weather.fragment.*;
import fr.xebia.xke.android.weather.request.FindPlaceRequest;
import fr.xebia.xke.android.weather.request.ForecastRequest;
import fr.xebia.xke.android.weather.request.response.ListPlaces;
import fr.xebia.xke.android.weather.request.response.WeatherForecastResponse;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@EActivity(R.layout.main)
public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener, WeatherLocationListener, FavoritesChangeListener {

    private static final String CURRENT_FRAGMENT_KEY = "currentFragment";


    @SystemService
    SearchManager mSearchManager;

    @InstanceState
    WeatherLocation mCurrentLocation;

    @InstanceState
    WeatherLocation mLocalizedLocation;

    @InstanceState
    String mFindPlaceRequestCacheKey;

    @InstanceState
    String mLoadForecastRequestCacheKey;

    @Bean
    WeatherLocationManager mLocationManager;

    @Bean
    WeatherActionBarManager mActionBarManager;

    @ViewById(R.id.left_drawer)
    ListView mLeftDrawer;

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bean
    CityListAdapter mDrawerAdapter;

    @Bean
    FavoritesManager mFavoritesManager;

    private final Handler mHandler = new Handler();

    private SelectPlaceFragment mSelectPlaceFragment;
    private ForecastWelcomeFragment mForecastWelcomeFragment;
    private SearchView mSearchView;
    private MenuItem mItemSelected;
    private BaseFragment mCurrentFragment;
    private ActionBarDrawerToggle mDrawerToggle;

    private boolean mLocalizationRequested;


    public void showForecastWelcomeFragment(WeatherLocation weatherLocation) {
        showForecastWelcomeFragment(weatherLocation, false);
    }

    protected void showForecastWelcomeFragment(WeatherLocation weatherLocation, boolean updateLocationWhenChanged) {
        if (weatherLocation != null) {
            loadForecast(weatherLocation);
        }

        mLocalizationRequested = updateLocationWhenChanged;
        showFragment(mForecastWelcomeFragment);

    }

    public void showSelectPlaceFragment() {
        showFragment(mSelectPlaceFragment);
    }

    public void setCurrentFragment(BaseFragment fragment) {
        mCurrentFragment = fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mItemSelected = item;

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        switch (item.getItemId()) {
            case R.id.menu_search:
                if (!mSelectPlaceFragment.isVisible()) {
                    showSelectPlaceFragment();
                }
                break;

            case R.id.menu_geolocalize:
                mLocalizationRequested = true;
                mLocationManager.requestPosition(this);
                break;

            case R.id.menu_addToFavorite:
                if (mCurrentLocation != null) {
                    mFavoritesManager.addLocationToFavorites(mCurrentLocation);
                }
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() >= 3) {
            loadPlaces(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        onQueryTextSubmit(newText);
        return true;
    }

    @Override
    public void onLocationChanged(WeatherLocation weatherLocation) {
        mLocalizedLocation = weatherLocation;
        mLocationManager.removeUpdates(this);
        mActionBarManager.setLocalizedLocation(weatherLocation);
        if (mLocalizationRequested) {
            loadForecast(weatherLocation);
            mLocalizationRequested = false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentFragment = (BaseFragment) getSupportFragmentManager().getFragment(savedInstanceState, CURRENT_FRAGMENT_KEY);
        }


    }


    @ItemClick(R.id.left_drawer)
    public void onNavigationItemClicked(int position) {
        WeatherLocation location = mDrawerAdapter.getItem(position);
        showForecastWelcomeFragment(location);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalizationRequested = mCurrentLocation == null;
        mLocationManager.requestPosition(this);
        mFavoritesManager.addListener(this);

    }

    @Override
    protected void onPause() {
        mLocationManager.removeUpdates(this);
        mFavoritesManager.removeListener(this);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCurrentFragment != null) {
            getSupportFragmentManager().putFragment(outState, CURRENT_FRAGMENT_KEY, mCurrentFragment);
        }
    }

    @AfterViews
    protected void afterActivityCreated() {

        mActionBarManager.init(mCurrentLocation);
        mActionBarManager.setLocalizedLocation(mLocalizedLocation);
        initNavigationDrawer();
        initFragments();

        mDrawerAdapter.setData(mFavoritesManager.getFavorites());

        mActionBarManager.showActionBar();

        if (mCurrentFragment == null || mCurrentFragment.equals(mForecastWelcomeFragment)) {
            showForecastWelcomeFragment(mCurrentLocation, true);
        } else if (mCurrentFragment.equals(mSelectPlaceFragment)) {
            showSelectPlaceFragment();
        }

    }

    private void initNavigationDrawer() {


        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mLeftDrawer.setAdapter(mDrawerAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, 0, 0) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final int width = drawerView.getLayoutParams().width;
                mCurrentFragment.getView().setTranslationX(slideOffset * width);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initFragments() {
        if (mCurrentFragment != null) {
            if (SelectPlaceFragment.TAG.equals(mCurrentFragment.getFragmentTag())) {
                mSelectPlaceFragment = (SelectPlaceFragment) mCurrentFragment;
            } else if (ForecastWelcomeFragment.TAG.equals(mCurrentFragment.getFragmentTag())) {
                mForecastWelcomeFragment = (ForecastWelcomeFragment) mCurrentFragment;
            }
        }

        if (mSelectPlaceFragment == null) {
            mSelectPlaceFragment = SelectPlaceFragment_.builder().build();
        }

        if (mForecastWelcomeFragment == null) {
            mForecastWelcomeFragment = ForecastWelcomeFragment_.builder().build();
        }
    }

    @Background
    protected void showFragment(BaseFragment fragment) {

        if (fragment == null) {
            throw new IllegalArgumentException("fragment to show must not be null.");
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

//        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        if (mCurrentFragment != null) {

            final String currentFragmentTag = mCurrentFragment.getFragmentTag();

            if (!currentFragmentTag.equals(fragment.getFragmentTag()) &&
                    !(currentFragmentTag.equals(SelectPlaceFragment.TAG))) {

                ft.addToBackStack(null);
            }

            ft.detach(mCurrentFragment);
        }

        ft.replace(R.id.container, fragment, fragment.getFragmentTag())
                .attach(fragment);


        ft.commit();

        if (mDrawerLayout.isDrawerOpen(mLeftDrawer)) {
            toggleDrawer();
        }

    }


    @UiThread
    protected void toggleDrawer() {
        mDrawerLayout.closeDrawer(mLeftDrawer);
    }

    private void collapseSearchView() {
        if (mItemSelected != null) {
            mItemSelected.collapseActionView();
            mSearchView.setQuery("", false);
            mSearchView.clearFocus();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void loadPlaces(String placeQuery) {

        spiceManager.cancel(ListPlaces.class, mFindPlaceRequestCacheKey);

        FindPlaceRequest findPlaceRequest = new FindPlaceRequest(placeQuery);
        mFindPlaceRequestCacheKey = "FindPlaceRequestCacheKey." + placeQuery;

        spiceManager.execute(findPlaceRequest, mFindPlaceRequestCacheKey, DurationInMillis.ALWAYS_EXPIRED, new FindPlaceRequestListener());
    }

    public void loadForecast(final WeatherLocation weatherLocation) {
        final String apiKey = "27843f1c90d943718611c202b9754378";


        spiceManager.cancel(WeatherForecast.class, mLoadForecastRequestCacheKey);

        ForecastRequest forecastRequest = new ForecastRequest(weatherLocation, apiKey);
        mLoadForecastRequestCacheKey = "LoadForecastRequestCacheKey." + weatherLocation.getCity() + "," + weatherLocation.getProvince() + "," + weatherLocation.getCountry();


//        ForecastRequest forecastRequest = new ForecastMockRequest(this);
        spiceManager.execute(forecastRequest, mLoadForecastRequestCacheKey, DurationInMillis.ONE_HOUR, new ForecastRequestListener());
    }

    @Override
    public void onFavoriteAdded(WeatherLocation location) {
        mDrawerAdapter.addData(location);
        Toast.makeText(this, getResources().getString(R.string.added_favorites, location.getCity()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavoriteRemoved(WeatherLocation location) {
        mDrawerAdapter.removeData(location);
        Toast.makeText(this, getResources().getString(R.string.removed_favorites, location.getCity()), Toast.LENGTH_SHORT).show();
    }


    private class FindPlaceRequestListener implements RequestListener<ListPlaces> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, "Fail to load cities", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final ListPlaces places) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mSelectPlaceFragment.isVisible()) {
                        mSelectPlaceFragment.updateUI(places);
                    }
                }
            });

        }
    }


    private class ForecastRequestListener implements RequestListener<WeatherForecastResponse> {


        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //TODO Afficher qu'il est impossible de récupérer les données météo
            Toast.makeText(MainActivity.this, "Fail to load weather forecasts", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final WeatherForecastResponse response) {

            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    mCurrentLocation = response.getWeatherLocation();
                    mActionBarManager.updateActionBar(mCurrentLocation);

                    collapseSearchView();

                    if (mForecastWelcomeFragment.isVisible())

                    {
                        mForecastWelcomeFragment.updateUI(response.getForecast(), mCurrentLocation);
                    }
                }
            }, 300);

        }
    }
}

