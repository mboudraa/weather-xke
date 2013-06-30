package fr.xebia.xke.android.weather.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.googlecode.androidannotations.annotations.EActivity;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import de.akquinet.android.androlog.Log;
import fr.xebia.xke.android.weather.request.WeatherSpiceService;

/**
 * User: mounirboudraa
 * Date: 24/05/13
 * Time: 12:47
 */
@EActivity
public abstract class BaseActivity extends FragmentActivity {

    protected final SpiceManager spiceManager = new SpiceManager(WeatherSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.init(this, "androlog.properties");
        Log.d(getClass().getName(), "onCreate -> " + this);
    }


    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(this);
        Log.d(getClass().getName(), "onStart -> " + this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getClass().getName(), "onResume -> " + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getClass().getName(), "onPause -> " + this);
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
        Log.d(getClass().getName(), "onStop -> " + this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getName(), "onDestroy -> " + this);
    }
}
