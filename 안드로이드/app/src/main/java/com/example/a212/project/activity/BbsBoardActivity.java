package com.example.a212.project.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a212.project.request.BbsBoardRequest;
import com.example.a212.project.R;

import org.json.JSONObject;

public class BbsBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_board);
        Intent intent = getIntent();
        final String getBbsID = intent.getStringExtra("getBbsID");
        final String getBbsTitle = intent.getStringExtra("getBbsTitle");
        final String userID = intent.getStringExtra("userID");
        final String getUserID = intent.getStringExtra("getUserID");
        String getBbsDate = intent.getStringExtra("getBbsDate");
        final String getContent = intent.getStringExtra("getContent");
        String getBbsAvailable = intent.getStringExtra("getBbsAvailable");

        TextView  BbsTitle = (TextView)findViewById(R.id.BbsTitle);
        TextView  UserID = (TextView)findViewById(R.id.BbsUserID);
        TextView  BbsDate = (TextView)findViewById(R.id.BbsDate);
        TextView  Content = (TextView)findViewById(R.id.BbsContent);
        Content.setMovementMethod(new ScrollingMovementMethod());




        Button Update = (Button)findViewById(R.id.Update);
        Button Delete = (Button)findViewById(R.id.Delete);
        Button board_main = (Button)findViewById(R.id.board_main);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BbsBoardActivity.this, boardUpdate.class);
                intent.putExtra("userID", userID);//현재의나
                intent.putExtra("getBbsTitle", getBbsTitle);//현재의나
                intent.putExtra("getContent", getContent);//현재의나
                intent.putExtra("getUserID", getUserID);//현재의나
                intent.putExtra("getBbsID", getBbsID);//현재의나
                startActivity(intent);
                finish();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try { //해당 웹사이트에 접속한 이후, 특정한 응답을 받을수 있게 함
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BbsBoardActivity.this);
                                builder.setMessage("현재 글을 삭제하시겠습니까?")
                                        .create();
                                builder.setNegativeButton("취소", null);
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(userID.equals(getUserID)) {
                                            Intent intent = new Intent(BbsBoardActivity.this, board_main.class);
                                            intent.putExtra("userID", userID);//현재의나
                                            startActivity(intent);
                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(BbsBoardActivity.this);
                                            builder.setMessage("작성자가 아닙니다.")
                                                    .create();
                                            builder.setNegativeButton("취소", null);
                                            builder.show();

                                        }
                                    }
                                });
                                builder.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BbsBoardActivity.this);
                                builder.setMessage("삭제하지 못하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                builder.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                BbsBoardRequest registerRequest = new BbsBoardRequest(userID, getBbsTitle, getContent, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BbsBoardActivity.this); //큐로 보낸다.
                queue.add(registerRequest);
            }
        });

        board_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BbsBoardActivity.this, board_main.class);
                intent.putExtra("userID", userID);//현재의나
                startActivity(intent);
            }
        });
        BbsTitle.setText(getBbsTitle);
        UserID.setText(getUserID);
        BbsDate.setText(getBbsDate);
        Content.setText(getContent);


    }
}
