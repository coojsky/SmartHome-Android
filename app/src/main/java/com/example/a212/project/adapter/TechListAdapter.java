package com.example.a212.project.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a212.project.request.AddLogRequest;
import com.example.a212.project.R;
import com.example.a212.project.object.Tech;
import com.example.a212.project.activity.MainActivity;
import com.example.a212.project.request.AddRequest;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by 212 on 2017-05-31.
 */

public class TechListAdapter extends BaseAdapter {

    private Context context;
    private List<Tech> techList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    public static int totalCredit = 0;

    public TechListAdapter(Context context, List<Tech> techList, Fragment parent) {
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
        View v = View.inflate(context, R.layout.tech, null); //course레이아웃 모든 변수
        TextView Room = (TextView) v.findViewById(R.id.Room);
        TextView Tech = (TextView) v.findViewById(R.id.Tech);
        final Button Switch = (Button) v.findViewById(R.id.Switch);
        Room.setText(techList.get(i).getRoom());
        Tech.setText(techList.get(i).getTech());
        Switch.setText(techList.get(i).getSwitch());

        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭시 버튼 바뀜
                        String userID = MainActivity.userID;
                        Response.Listener<String> responseListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try { //해당 웹사이트에 접속한 이후, 특정한 응답을 받을수 있게 함
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
//                                        if(techList.get(i).getSwitch().equals("ON")){
//                                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
//                                            AlertDialog dialog = builder.setMessage("On스위치")
//                                                    .setPositiveButton("확인", null)
//                                                    .create();
//                                            dialog.show();
//
//                                        }else{
//                                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
//                                            AlertDialog dialog = builder.setMessage("Off스위치")
//                                                    .setPositiveButton("확인", null)
//                                                    .create();
//                                            dialog.show();
//                                        }
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                        AlertDialog dialog = builder.setMessage("실패하였습니다.")
                                                .setNegativeButton("다시시도", null)
                                                .create();
                                        dialog.show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                if(techList.get(i).getSwitch().equals("ON")){
                    Switch.setText(techList.get(i).ONSwitch());
                    techList.get(i).setSwitch("OFF");
                }else{
                    techList.get(i).setSwitch("ON");
                    Switch.setText(techList.get(i).OFFSwitch());
                }


                AddRequest addRequest = new AddRequest(techList.get(i).getSwitch(),techList.get(i).getRoom(),techList.get(i).getTech() + "", responseListener); //""붙여서 문자열로 바꾼다.
                        RequestQueue queue = Volley.newRequestQueue(parent.getActivity()); //큐로 보낸다.
                        queue.add(addRequest);
                        //AddRequest가 실행 될 수 있도록 한다. 수강신청강의 추가

                AddLogRequest addLogRequest = new AddLogRequest(techList.get(i).getSwitch(),techList.get(i).getRoom(),techList.get(i).getTech(), userID, responseListener); //""붙여서 문자열로 바꾼다.
                RequestQueue logqueue = Volley.newRequestQueue(parent.getActivity()); //큐로 보낸다.
                logqueue.add(addLogRequest);

                    }
                });



        return v;
    }
}
