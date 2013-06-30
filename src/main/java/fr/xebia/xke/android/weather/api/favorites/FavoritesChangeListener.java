package fr.xebia.xke.android.weather.api.favorites;

import fr.xebia.xke.android.weather.api.location.WeatherLocation;

/**
 * User: mounirboudraa
 * Date: 05/06/13
 * Time: 00:24
 */
public interface FavoritesChangeListener {

    void onFavoriteAdded(WeatherLocation location);

    void onFavoriteRemoved(WeatherLocation location);
}
