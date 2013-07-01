package fr.xebia.xke.android.weather.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.SystemService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.api.favorites.FavoritesChangeListener;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.api.location.WeatherLocationListener;
import fr.xebia.xke.android.weather.fragment.BaseFragment;
import fr.xebia.xke.android.weather.fragment.ForecastWelcomeFragment;
import fr.xebia.xke.android.weather.fragment.SelectPlaceFragment;
import fr.xebia.xke.android.weather.request.response.ListPlaces;
import fr.xebia.xke.android.weather.request.response.WeatherForecastResponse;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@EActivity(R.layout.main)
public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener, WeatherLocationListener, FavoritesChangeListener {

    private static final String CURRENT_FRAGMENT_KEY = "currentFragment";

    @SystemService
    SearchManager mSearchManager;

    private SelectPlaceFragment mSelectPlaceFragment;
    private ForecastWelcomeFragment mForecastWelcomeFragment;
    private SearchView mSearchView;
    private MenuItem mItemSelected;
    private BaseFragment mCurrentFragment;

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
        //TODO Sauvegarder l'etat du fragment courant
    }

    @AfterViews
    protected void afterActivityCreated() {

        //TODO Initialiser l'action bar

        //TODO initialiser la vue

    }


    private void initFragments() {
        //TODO Initialiser les fragments
    }

    protected void showFragment(BaseFragment fragment) {
        if (fragment == null) {
            throw new IllegalArgumentException("fragment to show must not be null.");
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        if (mCurrentFragment != null) {

            final String currentFragmentTag = mCurrentFragment.getFragmentTag();

            if (!currentFragmentTag.equals(fragment.getFragmentTag()) &&
                    !(currentFragmentTag.equals(SelectPlaceFragment.TAG))) {

                ft.addToBackStack(null);
            }

        }

        ft.replace(R.id.container, fragment, fragment.getFragmentTag());

        ft.commit();

    }


    public void loadPlaces(String placeQuery) {
        //TODO Effectuer la recherche de ville
    }

    public void loadForecast(final WeatherLocation weatherLocation) {
        //TODO  Effectuer l'appel au web service de météo
    }

    @Override
    public void onFavoriteAdded(WeatherLocation location) {
        //TODO Favoris ajouté
    }

    @Override
    public void onFavoriteRemoved(WeatherLocation location) {
        //TODO Favoris supprimé
    }


    private class FindPlaceRequestListener implements RequestListener<ListPlaces> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //TODO Afficher que la recherche d'une ville est impossible
        }

        @Override
        public void onRequestSuccess(final ListPlaces places) {
            //TODO Mettre à jour la vue de recherche de ville
        }
    }


    private class ForecastRequestListener implements RequestListener<WeatherForecastResponse> {


        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //TODO Afficher qu'il est impossible de récupérer les données météo
        }

        @Override
        public void onRequestSuccess(final WeatherForecastResponse response) {
            //TODO Mettre à jour la vue principale
        }
    }
}

