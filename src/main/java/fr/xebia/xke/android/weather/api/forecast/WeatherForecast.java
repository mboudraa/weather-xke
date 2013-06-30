package fr.xebia.xke.android.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User: mounirboudraa
 * Date: 26/05/13
 * Time: 20:41
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecast implements Parcelable {
    private double latitude;
    private double longitude;
    private String timezone;
    private int offset;
    private HourlyWeatherDataPoint currently;
    private HourlyWeatherDataBlock hourly;
    private DailyWeatherDataBlock daily;


    public WeatherForecast() {

    }

    private WeatherForecast(Parcel in) {

        latitude=in.readDouble();
        longitude=in.readDouble();
        offset=in.readInt();
        currently=in.readParcelable(getClass().getClassLoader());
        hourly=in.readParcelable(getClass().getClassLoader());
        daily=in.readParcelable(getClass().getClassLoader());
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public HourlyWeatherDataPoint getCurrently() {
        return currently;
    }

    public void setCurrently(HourlyWeatherDataPoint currently) {
        this.currently = currently;
    }

    public HourlyWeatherDataBlock getHourly() {
        return hourly;
    }

    public void setHourly(HourlyWeatherDataBlock hourly) {
        this.hourly = hourly;
    }

    public DailyWeatherDataBlock getDaily() {
        return daily;
    }

    public void setDaily(DailyWeatherDataBlock daily) {
        this.daily = daily;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeInt(offset);
        out.writeParcelable(currently, flags);
        out.writeParcelable(hourly, flags);
        out.writeParcelable(daily, flags);
    }

    public static final Parcelable.Creator<WeatherForecast> CREATOR = new Parcelable.Creator<WeatherForecast>() {
        public WeatherForecast createFromParcel(Parcel in) {
            return new WeatherForecast(in);
        }

        public WeatherForecast[] newArray(int size) {
            return new WeatherForecast[size];
        }
    };

}
