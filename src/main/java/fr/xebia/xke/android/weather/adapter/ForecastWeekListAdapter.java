package fr.xebia.xke.android.weather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import fr.xebia.xke.android.weather.api.forecast.DailyWeatherDataPoint;
import fr.xebia.xke.android.weather.view.ForecastWeekItemView;
import fr.xebia.xke.android.weather.view.ForecastWeekItemView_;

import java.util.ArrayList;

/**
 * User: mounirboudraa
 * Date: 22/05/13
 * Time: 21:34
 */
@EBean
public class ForecastWeekListAdapter extends BaseAdapter {


    private final ArrayList<DailyWeatherDataPoint> data = new ArrayList<DailyWeatherDataPoint>();

    @RootContext
    Context context;





    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DailyWeatherDataPoint getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ForecastWeekItemView view;
        if (convertView == null) {
            view = ForecastWeekItemView_.build(context);
        } else {
            view = (ForecastWeekItemView) convertView;
        }

        view.bind(getItem(position));
        return view;
    }


    public void setData(ArrayList<DailyWeatherDataPoint> data) {
        this.data.clear();
        if (data != null && !data.isEmpty()) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
}
