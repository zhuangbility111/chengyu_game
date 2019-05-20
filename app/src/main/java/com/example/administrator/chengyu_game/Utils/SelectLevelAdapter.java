package com.example.administrator.chengyu_game.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.chengyu_game.R;

import java.util.List;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class SelectLevelAdapter extends BaseAdapter {

    private Context context;
    private List<String> selectLevelList;

    public class ViewHolder{
        public TextView mTvSelectLevel;
    }

    public SelectLevelAdapter(Context context, List<String> selectLevelList) {
        this.context = context;
        this.selectLevelList = selectLevelList;
    }

    @Override
    public int getCount() {
        return selectLevelList.size();
    }

    @Override
    public Object getItem(int i) {
        return selectLevelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.gridview_show_level_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.mTvSelectLevel = (TextView) convertView.findViewById(R.id.mTvShowSelectLevel);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvSelectLevel.setText(String.valueOf(i+1));
        if ("1".equals(selectLevelList.get(i))) {
            viewHolder.mTvSelectLevel.setTextColor(Color.parseColor("#43CD80"));
        }
        else if ("0".equals(selectLevelList.get(i))) {
            viewHolder.mTvSelectLevel.setTextColor(Color.parseColor("#FF0000"));
        }

        return convertView;
    }
}
