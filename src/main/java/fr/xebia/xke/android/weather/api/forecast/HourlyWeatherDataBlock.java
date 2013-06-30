package fr.xebia.xke.android.weather.api.forecast;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by mounirboudraa on 27/05/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyWeatherDataBlock extends WeatherDataBlock {

    private List<HourlyWeatherDataPoint> data;

    public HourlyWeatherDataBlock() {

    }

    protected HourlyWeatherDataBlock(Parcel in) {
        super(in);
        data = in.readArrayList(getClass().getClassLoader());
    }


    public List<HourlyWeatherDataPoint> getData() {
        return data;
    }

    public void setData(List<HourlyWeatherDataPoint> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out,flags);
    }

    public static final Parcelable.Creator<HourlyWeatherDataBlock> CREATOR = new Parcelable.Creator<HourlyWeatherDataBlock>() {
        public HourlyWeatherDataBlock createFromParcel(Parcel in) {
            return new HourlyWeatherDataBlock(in);
        }

        public HourlyWeatherDataBlock[] newArray(int size) {
            return new HourlyWeatherDataBlock[size];
        }
    };
}
