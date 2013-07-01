package fr.xebia.xke.android.weather.api.location;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.api.Scope;
import de.akquinet.android.androlog.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mounirboudraa on 02/06/13.
 */
@EBean(scope = Scope.Singleton)
public class WeatherLocationManager implements LocationListener {

    @RootContext
    Context mContext;

    @SystemService
    LocationManager mLocationManager;

    private Criteria mCriteria = new Criteria();
    private Object mLocationListenersMutex = new Object();
    private ArrayList<WeatherLocationListener> mLocationListeners = new ArrayList<WeatherLocationListener>();

    @AfterInject
    protected void init() {

        mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        mCriteria.setPowerRequirement(Criteria.POWER_LOW);
        mCriteria.setAltitudeRequired(false);
        mCriteria.setBearingRequired(false);
        mCriteria.setSpeedRequired(false);

    }

    public void requestPosition(WeatherLocationListener listener) {
        synchronized (mLocationListenersMutex) {
            mLocationListeners.add(listener);
        }
        final String bestAvailableLocationProvider = mLocationManager.getBestProvider(mCriteria, true);
        if (bestAvailableLocationProvider == null) {
            //TODO Throw new NoLocationProviderAvailableException()
        } else {
            mLocationManager.requestSingleUpdate(bestAvailableLocationProvider, this, null);
            Location loc = mLocationManager.getLastKnownLocation(bestAvailableLocationProvider);
            notifyListeners(loc);
        }
    }

    public void removeUpdates(WeatherLocationListener listener) {
        synchronized (mLocationListenersMutex) {
            mLocationListeners.remove(listener);

            if (mLocationListeners.isEmpty()) {
                mLocationManager.removeUpdates(this);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocationManager.removeUpdates(this);
        notifyListeners(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Background
    protected void notifyListeners(Location location) {
        Geocoder geocoder = new Geocoder(mContext);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                final WeatherLocation newWeatherLocation = WeatherLocation
                        .LocationBuilder.newInstance()
                        .setLatitude(address.getLatitude())
                        .setLongitude(address.getLongitude())
                        .setCity(address.getLocality())
                        .setCountry(address.getCountryName())
                        .setProvince(address.getAdminArea())
                        .build();

                synchronized (mLocationListenersMutex) {
                    for (WeatherLocationListener listener : mLocationListeners) {
                        if (listener != null) {
                            listener.onLocationChanged(newWeatherLocation);
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e(e.getMessage());
        }
    }


}
