package com.example.a212.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a212.project.object.Bbs;
import com.example.a212.project.R;

import java.util.List;

/**
 * Created by 212 on 2017-05-31.
 */

public class bbsListAdapter extends BaseAdapter {

    private Context context;
    private List<Bbs> mArrayList;

    public bbsListAdapter(Context context, List<Bbs> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }



    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item_list,null);
        TextView bbsID= (TextView) v.findViewById(R.id.bbsID);
        TextView userID= (TextView) v.findViewById(R.id.userID);
        TextView bbsTitle= (TextView) v.findViewById(R.id.bbsTitle);
        TextView bbsDate= (TextView) v.findViewById(R.id.bbsDate);
        LinearLayout bbs = (LinearLayout) v.findViewById(R.id.bbs);
        bbsID.setText(mArrayList.get(i).getBbsID()+"");
        userID.setText(mArrayList.get(i).getUserID());
        bbsTitle.setText(mArrayList.get(i).getBbsTitle());
        bbsDate.setText(mArrayList.get(i).getBbsDate());

        return v;


    }
}
