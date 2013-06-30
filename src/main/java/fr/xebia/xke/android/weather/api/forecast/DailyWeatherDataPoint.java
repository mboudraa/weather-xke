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
public class DailyWeatherDataPoint extends WeatherDataPoint {

    private double precipIntensityMax;
    private long precipIntensityMaxTime;
    private double precipAccumulation;
    private double temperatureMin;
    private long temperatureMinTime;
    private double temperatureMax;
    private long temperatureMaxTime;
    private long sunriseTime;
    private long sunsetTime;


    public DailyWeatherDataPoint() {

    }

    protected DailyWeatherDataPoint(Parcel in) {
        super(in);
        precipIntensityMax = in.readDouble();
        precipIntensityMaxTime = in.readLong();
        precipAccumulation = in.readDouble();
        temperatureMin = in.readDouble();
        temperatureMinTime = in.readLong();
        temperatureMax = in.readDouble();
        temperatureMaxTime = in.readLong();
        sunriseTime = in.readLong();
        sunsetTime = in.readLong();
    }

    public long getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    public void setPrecipIntensityMaxTime(long precipIntensityMaxTime) {
        this.precipIntensityMaxTime = precipIntensityMaxTime;
    }

    public double getPrecipAccumulation() {
        return precipAccumulation;
    }

    public void setPrecipAccumulation(double precipAccumulation) {
        this.precipAccumulation = precipAccumulation;
    }

    public double getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    public void setPrecipIntensityMax(double precipIntensityMax) {
        this.precipIntensityMax = precipIntensityMax;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public long getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(long temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public long getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(long temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(long sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeDouble(precipIntensityMax);
        out.writeLong(precipIntensityMaxTime);
        out.writeDouble(precipAccumulation);
        out.writeDouble(temperatureMin);
        out.writeLong(temperatureMinTime);
        out.writeDouble(temperatureMax);
        out.writeLong(temperatureMaxTime);
        out.writeLong(sunriseTime);
        out.writeLong(sunsetTime);
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
