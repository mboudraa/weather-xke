package fr.xebia.xke.android.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User: mounirboudraa
 * Date: 26/05/13
 * Time: 21:52
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyWeatherDataPoint extends WeatherDataPoint {

    private double temperature;

    public HourlyWeatherDataPoint() {

    }

    protected HourlyWeatherDataPoint(Parcel in) {
        super(in);
        temperature = in.readDouble();
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out,flags);
    }

    public static final Parcelable.Creator<HourlyWeatherDataPoint> CREATOR = new Parcelable.Creator<HourlyWeatherDataPoint>() {
        public HourlyWeatherDataPoint createFromParcel(Parcel in) {
            return new HourlyWeatherDataPoint(in);
        }

        public HourlyWeatherDataPoint[] newArray(int size) {
            return new HourlyWeatherDataPoint[size];
        }
    };
}
