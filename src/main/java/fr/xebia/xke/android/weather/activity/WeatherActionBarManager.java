package fr.xebia.xke.android.weather.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.UiThread;
import fr.xebia.xke.android.weather.R;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.api.location.WeatherLocationManager;

/**
 * Created by mounirboudraa on 02/06/13.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@EBean
public class WeatherActionBarManager {

    private ActionBar mActionBar;
    private WeatherLocation mLocalizedWeatherLocation;
    private TextView mActionBarTitle;
    private TextView mActionBarSubTitle;
    private Drawable mActionBarTitleIcon;
    private WeatherLocation mCurrentWeatherLocation;

    @RootContext
    BaseActivity mContext;

    //TODO Add the correct annotation to inject mLocationManager
    WeatherLocationManager mLocationManager;


    public void init(WeatherLocation weatherLocation) {
        mActionBar = mContext.getActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        if (mActionBarTitleIcon == null) {
            mActionBarTitleIcon = mContext.getResources()
                                          .getDrawable(R.drawable.ic_current_location);
        }
        View customTitle = mContext.getLayoutInflater().inflate(R.layout.actionbar_title_layout, null);
        mActionBar.setCustomView(customTitle);

        mActionBarTitle = (TextView) customTitle.findViewById(R.id.actionbar_title_textview);
        mActionBarSubTitle = (TextView) customTitle.findViewById(R.id.actionbar_subtitle_textview);

        if (weatherLocation != null) {
            updateActionBar(weatherLocation);
        } else {
            mActionBarTitle.setText(R.string.app_name);
        }

    }


    @UiThread
    public void setLocalizedLocation(WeatherLocation location) {
        mLocalizedWeatherLocation = location;
        updateActionBarTitleIcon();
    }

    @UiThread
    public void updateActionBar(final WeatherLocation weatherLocation) {
        mActionBarTitle.setText(weatherLocation.getCity());
        mActionBarSubTitle.setText(String.format("%s, %s", weatherLocation.getProvince(), weatherLocation.getCountry()));

        mCurrentWeatherLocation = weatherLocation;
        updateActionBarTitleIcon();
    }


    private void updateActionBarTitleIcon() {
        if (mCurrentWeatherLocation != null && mCurrentWeatherLocation.equals(mLocalizedWeatherLocation)) {
            boolean updateIcon = false;
            updateIcon = mActionBarTitle.getCompoundDrawables()[0] == null;

            if (updateIcon) {
                setActionBarTitleIcon(mActionBarTitleIcon);
            }
        } else {
            setActionBarTitleIcon(null);
        }
    }

    private void setActionBarTitleIcon(Drawable icon) {
        mActionBarTitle.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        mActionBarTitle.invalidate();

    }

    public void showActionBar() {
        mActionBar.show();
    }


}
