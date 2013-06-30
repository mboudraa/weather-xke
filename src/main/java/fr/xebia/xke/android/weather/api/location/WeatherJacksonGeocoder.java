package fr.xebia.xke.android.weather.api.location;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.*;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.EnumMap;
import java.util.Map;

/**
 * User: mounirboudraa
 * Date: 25/05/13
 * Time: 22:49
 */
public class WeatherJacksonGeocoder extends Geocoder {

    private static final String GEOCODE_REQUEST_QUERY_BASIC = "/maps/api/geocode/json?sensor=false";
    private static final String ENCODING = "UTF-8";


    public GeocodeResponse geocode(final GeocoderRequest geocoderRequest) {


        try {

            final String urlString = getURL(geocoderRequest);

            return request(urlString);
        } catch (Exception e) {
            //TODO Log exception
            return null;
        }
    }

    protected GeocodeResponse request(String urlString) throws IOException {


        URL url = new URL(urlString);
        Reader reader = new BufferedReader(new InputStreamReader(url.openStream(), ENCODING));

        JsonParser jsonParser = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy());
            JsonFactory factory = mapper.getFactory();
            jsonParser = factory.createParser(reader);
            return jsonParser.readValueAs(GeocodeResponse.class);
        } finally {
            if (jsonParser != null) {
                jsonParser.close();
            }
            reader.close();
        }


    }

    protected StringBuilder getURLQuery(GeocoderRequest geocoderRequest) throws UnsupportedEncodingException {
        final String channel = geocoderRequest.getChannel();
        final String address = geocoderRequest.getAddress();
        final LatLngBounds bounds = geocoderRequest.getBounds();
        final String language = geocoderRequest.getLanguage();
        final String region = geocoderRequest.getRegion();
        final LatLng location = geocoderRequest.getLocation();
        final EnumMap<GeocoderComponent, String> components = geocoderRequest.getComponents();

        final StringBuilder url = new StringBuilder(GEOCODE_REQUEST_QUERY_BASIC);

        if (channel != null && channel.length() > 0) {
            url.append("&channel=").append(URLEncoder.encode(channel, ENCODING));
        }
        if (address != null && address.length() > 0) {
            url.append("&address=").append(URLEncoder.encode(address, ENCODING));
        } else if (location != null) {
            url.append("&latlng=").append(URLEncoder.encode(location.toUrlValue(), ENCODING));
        } else if (!components.isEmpty()) {
            url.append("&components=");
            boolean isFirstLine = true;
            for (Map.Entry<GeocoderComponent, String> entry : components.entrySet()) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    url.append(URLEncoder.encode("|", ENCODING));
                }
                url.append(URLEncoder.encode(entry.getKey().value(), ENCODING));
                url.append(':');
                url.append(URLEncoder.encode(entry.getValue(), ENCODING));
            }

        } else {
            throw new IllegalArgumentException("Address or location or Components must be defined");
        }
        if (language != null && language.length() > 0) {
            url.append("&language=").append(URLEncoder.encode(language, ENCODING));
        }
        if (region != null && region.length() > 0) {
            url.append("&region=").append(URLEncoder.encode(region, ENCODING));
        }
        if (bounds != null) {
            url.append("&bounds=").append(URLEncoder.encode(bounds.getSouthwest().toUrlValue() + "|" + bounds.getNortheast().toUrlValue(), ENCODING));
        }


        return url;
    }


}