package com.example.a212.project.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a212.project.R;
import com.example.a212.project.object.log;

import java.util.List;

/**
 * Created by 212 on 2017-05-31.
 */

public class LogListAdapter extends BaseAdapter {

    private Context context;
    private List<log> LogList;
    private Fragment parent;

    public LogListAdapter(Context context, List<log> LogList, Fragment parent) {
        this.context = context;
        this.LogList = LogList;
        this.parent = parent;
    }


    @Override
    public int getCount() {
        return LogList.size();
    }

    @Override
    public Object getItem(int i) {
        return LogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //특정한 강의를 추가하는 이벤트
        View v = View.inflate(context, R.layout.log, null); //course레이아웃 모든 변수
        TextView UserID = (TextView) v.findViewById(R.id.UserID);
        TextView Date = (TextView) v.findViewById(R.id.Date);
        TextView Room = (TextView) v.findViewById(R.id.Room);
        TextView Tech = (TextView) v.findViewById(R.id.Tech);
        TextView Switch = (TextView) v.findViewById(R.id.Switch);
        UserID.setText(LogList.get(i).getUserID());
        Date.setText(LogList.get(i).getDate());
        Room.setText(LogList.get(i).getRoom());
        Tech.setText(LogList.get(i).getTech());
        Switch.setText(LogList.get(i).getSwitch());

        return v;
    }
}
