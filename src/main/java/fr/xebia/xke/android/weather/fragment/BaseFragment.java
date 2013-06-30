package fr.xebia.xke.android.weather.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.xebia.xke.android.weather.activity.MainActivity;

/**
 * User: mounirboudraa
 * Date: 24/05/13
 * Time: 15:59
 */
public abstract class BaseFragment extends Fragment {


    protected MainActivity mActivity;

    public abstract String getFragmentTag();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }


    @Override
    public void onStart() {
        super.onStart();
        mActivity.setCurrentFragment(this);
    }

}
