package fr.xebia.xke.android.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by mounirboudraa on 27/05/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeatherDataBlock extends WeatherDataBlock {

    private List<DailyWeatherDataPoint> data;


    public DailyWeatherDataBlock() {

    }

    protected DailyWeatherDataBlock(Parcel in) {
        super(in);
        data = in.readArrayList(getClass().getClassLoader());
    }


    public List<DailyWeatherDataPoint> getData() {
        return data;
    }

    public void setData(List<DailyWeatherDataPoint> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
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
