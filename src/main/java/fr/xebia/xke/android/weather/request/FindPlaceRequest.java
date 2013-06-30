package fr.xebia.xke.android.weather.request;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import fr.xebia.xke.android.weather.api.location.WeatherJacksonGeocoder;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.request.response.ListPlaces;

import java.util.List;

/**
 * User: mounirboudraa
 * Date: 25/05/13
 * Time: 16:14
 */
public class FindPlaceRequest extends SpringAndroidSpiceRequest<ListPlaces> {

    private  String placeRequest;

    public FindPlaceRequest(String placeRequest) {
        super(ListPlaces.class);
        this.placeRequest = placeRequest;
    }

    @Override
    public ListPlaces loadDataFromNetwork() throws Exception {

        Geocoder geocoder = new WeatherJacksonGeocoder();

        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
                .addComponent(GeocoderComponent.LOCALITY, placeRequest.trim())
                .getGeocoderRequest();

        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);

 //TODO Remonter l'exception quand geocoderResponse == null pour la récuperer dans le fragment

        ListPlaces places = new ListPlaces();

        List<GeocoderResult> results =  geocoderResponse.getResults();
        for (GeocoderResult geocoderResult : results) {

            LatLng location = geocoderResult.getGeometry().getLocation();
            List<String> locationTypes = geocoderResult.getTypes();

            WeatherLocation.LocationBuilder locationBuilder =  WeatherLocation.LocationBuilder.newInstance();

            if (locationTypes != null && locationTypes.contains("locality")) {

                for (GeocoderAddressComponent address : geocoderResult.getAddressComponents()) {

                    List<String> addressTypes = address.getTypes();
                    String componentName = address.getLongName();

                    if (addressTypes.contains("locality")) {
                        locationBuilder.setCity(componentName);
                    } else if (addressTypes.contains("administrative_area_level_1")) {
                        locationBuilder.setProvince(componentName);
                    } else if (addressTypes.contains("country")) {
                        locationBuilder.setCountry(componentName);
                    }
                }

                locationBuilder.setLatitude(location.getLat().doubleValue());
                locationBuilder.setLongitude(location.getLng().doubleValue());

                places.add(locationBuilder.build());
            }

        }

        return places;
    }
}
