package fr.xebia.xke.android.weather.api.favorites;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;

import java.util.ArrayList;

/**
 * User: mounirboudraa
 * Date: 05/06/13
 * Time: 00:17
 */
@EBean(scope = Scope.Singleton)
public class FavoritesManager {

    private ArrayList<FavoritesChangeListener> mListeners = new ArrayList<FavoritesChangeListener>();
    private ArrayList<WeatherLocation> mFavorites = new ArrayList<WeatherLocation>();

    private Object mListenersMutex = new Object();
    private Object mFavoritesMutex = new Object();


    public void addLocationToFavorites(WeatherLocation location) {
        notifyFavoriteAdded(location);
        synchronized (mFavoritesMutex) {
            if (!mFavorites.contains(location)) {
                mFavorites.add(location);
            }
        }
        //TODO Save favorites in database;

    }


    public void removeLocationToFavorites(WeatherLocation location) {
        notifyFavoriteremoved(location);
        synchronized (mFavoritesMutex) {
            mFavorites.remove(location);
        }
        //TODO Save favorites in database;
    }


    public ArrayList<WeatherLocation> getFavorites() {
        ArrayList<WeatherLocation> results = new ArrayList<WeatherLocation>();
        synchronized (mFavoritesMutex) {
            results.addAll(mFavorites);
        }
        return results;
    }

    public void addListener(FavoritesChangeListener listener) {
        synchronized (mListenersMutex) {
            mListeners.add(listener);
        }
    }


    public void removeListener(FavoritesChangeListener listener) {
        synchronized (mListenersMutex) {
            mListeners.remove(listener);
        }
    }


    private void notifyFavoriteAdded(WeatherLocation location) {
        synchronized (mListenersMutex) {
            for (FavoritesChangeListener listener : mListeners) {
                listener.onFavoriteAdded(location);
            }
        }
    }

    private void notifyFavoriteremoved(WeatherLocation location) {
        synchronized (mListenersMutex) {
            for (FavoritesChangeListener listener : mListeners) {
                listener.onFavoriteRemoved(location);
            }
        }
    }


}
