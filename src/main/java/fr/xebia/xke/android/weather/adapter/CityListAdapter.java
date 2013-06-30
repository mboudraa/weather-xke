package fr.xebia.xke.android.weather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import fr.xebia.xke.android.weather.api.location.WeatherLocation;
import fr.xebia.xke.android.weather.view.PlaceFoundItemView;
import fr.xebia.xke.android.weather.view.PlaceFoundItemView_;

import java.util.ArrayList;

/**
 * User: mounirboudraa
 * Date: 22/05/13
 * Time: 21:34
 */
@EBean
public class CityListAdapter extends BaseAdapter {


    private final ArrayList<WeatherLocation> mData = new ArrayList<WeatherLocation>();

    @RootContext
    Context context;


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public WeatherLocation getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlaceFoundItemView view;
        if (convertView == null) {
            view = PlaceFoundItemView_.build(context);
        } else {
            view = (PlaceFoundItemView) convertView;
        }

        view.bind(getItem(position));
        return view;
    }


    public void setData(ArrayList<WeatherLocation> data) {
        mData.clear();
        if (data != null && !data.isEmpty()) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(WeatherLocation data) {
        if (data != null && !mData.contains(data)) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }


    public void removeData(WeatherLocation data) {
        if (data != null && mData.contains(data)) {
            mData.remove(data);
            notifyDataSetChanged();
        }
    }
}
