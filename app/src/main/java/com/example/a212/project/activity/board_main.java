package com.example.a212.project.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.example.a212.project.object.Bbs;
import com.example.a212.project.R;
import com.example.a212.project.adapter.bbsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class board_main extends AppCompatActivity{
    private static String TAG = "phptest_MainActivity";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_Title = "title";
    private static final String TAG_Content ="content";


    ArrayList<Bbs> mArrayList;
    ListView mlistView;
    String mJsonString;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);
        userID = getIntent().getStringExtra("userID");

        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();

        BackgroundTask task = new BackgroundTask();
        task.execute("http://220.66.87.212:3335/getjson.php");
        Button btn = (Button) findViewById(R.id.button_btn1);


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),board.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });
    }
    public void onClick(View v){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
        finish();
    }

    private class BackgroundTask extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(board_main.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

            if (result == null){


            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject object = jsonArray.getJSONObject(i);


                int bbsID = object.getInt("bbsID");
                String bbsTitle = object.getString("bbsTitle");
                String userID = object.getString("userID");
                String bbsDate = object.getString("bbsDate");
                String bbsContent = object.getString("bbsContent");
                String bbsAvailable = object.getString("bbsAvailable");
                Bbs bbs = new Bbs(bbsID,bbsTitle,userID,bbsDate,bbsContent,bbsAvailable);
                mArrayList.add(bbs);
            }

            bbsListAdapter adapter = new bbsListAdapter(getApplicationContext(),mArrayList);
            mlistView.setAdapter(adapter);

            mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent =
                            new Intent(board_main.this, BbsBoardActivity.class);
                    int i = mArrayList.get(position).getBbsID();
                    intent.putExtra("getBbsID", String.valueOf(mArrayList.get(position).getBbsID()));
                    intent.putExtra("getBbsTitle", mArrayList.get(position).getBbsTitle());//글제목
                    intent.putExtra("userID", userID);//현재의나
                    intent.putExtra("getUserID", mArrayList.get(position).getUserID());//작성자
                    intent.putExtra("getBbsDate", mArrayList.get(position).getBbsDate());//작성일자
                    intent.putExtra("getContent", mArrayList.get(position).getContent());//작성내용
                    intent.putExtra("getBbsAvailable", String.valueOf(mArrayList.get(position).getBbsAvailable()));//작성내용

                    startActivity(intent);
                    finish();
                }
            });
        } catch (JSONException e) {
        }

    }

}