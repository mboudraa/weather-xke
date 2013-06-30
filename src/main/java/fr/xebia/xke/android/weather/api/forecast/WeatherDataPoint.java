package fr.xebia.xke.android.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User: mounirboudraa
 * Date: 26/05/13
 * Time: 21:39
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class WeatherDataPoint implements Parcelable {

    private long time;
    private String summary;
    private String icon;



    private double precipProbability;
    private double precipIntensity;
    private String precipType;
    private double dewPoint;
    private double windSpeed;
    private double windBearing;
    private double cloudCover;
    private double humidity;
    private double pressure;
    private double visibility;
    private double ozone;

    public WeatherDataPoint() {

    }

    protected WeatherDataPoint(Parcel in) {
        time = in.readLong();
        summary = in.readString();
        icon = in.readString();
        precipProbability = in.readDouble();
        precipIntensity = in.readDouble();
        precipType = in.readString();
        dewPoint = in.readDouble();
        windSpeed = in.readDouble();
        windBearing = in.readDouble();
        cloudCover = in.readDouble();
        humidity = in.readDouble();
        pressure = in.readDouble();
        visibility = in.readDouble();
        ozone = in.readDouble();
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public void setPrecipIntensity(double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(double windBearing) {
        this.windBearing = windBearing;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(time);
        out.writeString(summary);
        out.writeString(icon);
        out.writeDouble(precipProbability);
        out.writeDouble(precipIntensity);
        out.writeString(precipType);
        out.writeDouble(dewPoint);
        out.writeDouble(windSpeed);
        out.writeDouble(windBearing);
        out.writeDouble(cloudCover);
        out.writeDouble(humidity);
        out.writeDouble(pressure);
        out.writeDouble(visibility);
        out.writeDouble(ozone);
    }


}
