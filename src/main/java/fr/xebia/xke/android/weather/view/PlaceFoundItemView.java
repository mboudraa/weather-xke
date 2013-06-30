package fr.xebia.xke.android.weather.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;

/**
 * User: mounirboudraa
 * Date: 23/05/13
 * Time: 17:16
 */
@EViewGroup(R.layout.city_response_content)
public class PlaceFoundItemView extends LinearLayout {

    private StringBuilder detailStringBuilder = new StringBuilder();

    @ViewById
    TextView cityNameTextView;

    @ViewById
    TextView cityDetailTextView;

    public PlaceFoundItemView(Context context) {
        super(context);
    }


    public void bind(WeatherLocation weatherLocation) {
        detailStringBuilder.delete(0, detailStringBuilder.length());
        cityNameTextView.setText(weatherLocation.getCity());
        cityDetailTextView.setText(detailStringBuilder.append(weatherLocation.getCountry()).append(", ").append(weatherLocation
                .getProvince()));
    }
}
