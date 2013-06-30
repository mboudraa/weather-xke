package fr.xebia.xke.android.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by mounirboudraa on 27/05/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class WeatherDataBlock implements Parcelable {

    private String summary;
    private String icon;

    public WeatherDataBlock() {

    }

    protected WeatherDataBlock(Parcel in) {
        summary = in.readString();
        icon = in.readString();
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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(summary);
        out.writeString(icon);
    }
}
