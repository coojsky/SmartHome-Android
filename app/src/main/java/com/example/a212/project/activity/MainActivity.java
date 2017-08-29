package com.example.a212.project.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a212.project.object.Notice;
import com.example.a212.project.adapter.NoticeListAdapter;
import com.example.a212.project.R;
import com.example.a212.project.flagment.TechFragment;
import com.example.a212.project.flagment.UserFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //화면을 세로로 고정


        userID = getIntent().getStringExtra("userID");

        noticeListView  = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();


        final Button noticeButton = (Button) findViewById(R.id.noticeButton);
        final ImageButton stateButton = (ImageButton) findViewById(R.id.stateButton);
        final Button techButton = (Button) findViewById(R.id.techButton);
        final Button boardButton = (Button) findViewById(R.id.boardButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);
        noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        //공지사항으로 이동
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });


        stateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE); //공지사항 부분이 보이지 않도록
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                stateButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                techButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new UserFragment());
                fragmentTransaction.commit();
            }
        });

        techButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE); //공지사항 부분이 보이지 않도록
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                stateButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                techButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new TechFragment());
                fragmentTransaction.commit();
            }
        });
        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),board_main.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
    }
});


        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://220.66.87.212:3335/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=  new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void...values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while(count<jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count); // 배열의 원소값 저장
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);//하나의 공지사항에 대한 객체 생성
                    noticeList.add(notice);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            adapter = new NoticeListAdapter(getApplicationContext(),noticeList);
            noticeListView.setAdapter(adapter);
        }

    }

    private long lastTimeBackPressed; // 두번 뒤로가기 누르면!

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        Toast.makeText(this,"'뒤로'버튼을 한번 더 눌러 종료",Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }

}