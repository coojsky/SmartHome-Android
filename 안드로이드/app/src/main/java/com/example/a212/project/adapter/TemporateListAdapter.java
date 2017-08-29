package com.example.a212.project.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a212.project.R;
import com.example.a212.project.object.Tech;
import com.example.a212.project.activity.MainActivity;

import java.util.List;

/**
 * Created by 212 on 2017-05-31.
 */

public class TemporateListAdapter extends BaseAdapter {

    private Context context;
    private List<Tech> techList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    public static int totalCredit = 0;

    public TemporateListAdapter(Context context, List<Tech> techList, Fragment parent) {
        this.context = context;
        this.techList = techList;
        this.parent = parent;
        totalCredit = 0;
    }


    @Override
    public int getCount() {
        return techList.size();
    }

    @Override
    public Object getItem(int i) {
        return techList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //특정한 강의를 추가하는 이벤트
        View v = View.inflate(context, R.layout.temperate, null); //tech레이아웃 모든 변수
        TextView Tech = (TextView) v.findViewById(R.id.Tech);
        TextView Switch = (TextView) v.findViewById(R.id.temporate);
        Tech.setText(techList.get(i).getTech());
        Switch.setText(techList.get(i).getSwitch());

        return v;
    }
}
