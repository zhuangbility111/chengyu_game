package com.example.administrator.chengyu_game.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.chengyu_game.R;

import java.util.List;

/**
 * Created by Administrator on 2019/4/20 0020.
 */

public class ChengyuZisAdapter extends BaseAdapter {

    private Context context;
    private List<String> chengyuZiList;

    public class ViewHolder{
        public TextView mTvChengyuZi;
    }

    public ChengyuZisAdapter(Context context, List<String> chengyuZiList) {
        this.context = context;
        this.chengyuZiList = chengyuZiList;
    }

    @Override
    public int getCount() {
        return chengyuZiList.size();
    }

    @Override
    public Object getItem(int i) {
        return chengyuZiList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.gridview_showzi_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.mTvChengyuZi = (TextView) convertView.findViewById(R.id.mTvShowChengyuZi);
                convertView.setTag(viewHolder);
            }
            else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvChengyuZi.setText(chengyuZiList.get(i));
        return convertView;
    }


}
