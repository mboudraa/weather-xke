package fr.xebia.xke.android.weather.api.location;

/**
 * User: mounirboudraa
 * Date: 22/05/13
 * Time: 23:02
 */
//TODO Make it Parcelable to to allow the preservation of its state during configuration change
public class WeatherLocation {

    private String city;
    private String province;
    private String country;
    private double latitude;
    private double longitude;


    private WeatherLocation() {
    }


    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeatherLocation that = (WeatherLocation) o;

        if (city != null ? !city.equals(that.city) : that.city != null) {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null) {
            return false;
        }
        if (province != null ? !province.equals(that.province) : that.province != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    public static class LocationBuilder {

        private WeatherLocation weatherLocation;

        public static LocationBuilder newInstance(){
            return new LocationBuilder();
        }
        private LocationBuilder() {
            weatherLocation = new WeatherLocation();
        }

        public LocationBuilder setCity(String city) {
            weatherLocation.city = city;
            return this;
        }

        public LocationBuilder setProvince(String province) {
            weatherLocation.province = province;
            return this;
        }


        public LocationBuilder setCountry(String country) {
            weatherLocation.country = country;
            return this;
        }

        public LocationBuilder setLatitude(double lattitude) {
            weatherLocation.latitude = lattitude;
            return this;
        }

        public LocationBuilder setLongitude(double longitude) {
            weatherLocation.longitude = longitude;
            return this;
        }

        public WeatherLocation build() {
            return weatherLocation;
        }
    }
}
