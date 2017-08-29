package com.example.a212.project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a212.project.R;
import com.example.a212.project.request.boardUpdateRequest;

import org.json.JSONObject;

public class boardUpdate extends AppCompatActivity {
    private static String TAG = "phptest_MainActivity";
    private EditText mEditTextName;
    private EditText mEditTextAddress;
    String userID;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardupdate);
        mEditTextName = (EditText)findViewById(R.id.Title);
        mEditTextAddress = (EditText)findViewById(R.id.Content);
        userID = getIntent().getStringExtra("userID");
        final String getBbsTitle = getIntent().getStringExtra("getBbsTitle");
        String getContent = getIntent().getStringExtra("getContent");
        final String getBbsID = getIntent().getStringExtra("getBbsID");
        final String getUserID = getIntent().getStringExtra("getUserID");
        mEditTextName.setText(getBbsTitle);
        mEditTextAddress.setText(getContent);

        Button buttonInsert = (Button)findViewById(R.id.update);
        Button menu = (Button)findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),board_main.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bbsTitle = mEditTextName.getText().toString();
                String bbsContent = mEditTextAddress.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{ //해당 웹사이트에 접속한 이후, 특정한 응답을 받을수 있게 함
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(boardUpdate.this);
                                builder.setMessage("현재 글을 수정하시겠습니까?")
                                        .create();
                                builder.setNegativeButton("취소", null);
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(userID.equals(getUserID)) {
                                            Intent intent = new Intent(boardUpdate.this, board_main.class);
                                            intent.putExtra("userID", userID);//현재의나
                                            startActivity(intent);
                                        }else{
                                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(boardUpdate.this);
                                            builder.setMessage("작성자가 아닙니다.")
                                                    .create();
                                            builder.setNegativeButton("취소", null);
                                            builder.show();
                                        }
                                    }
                                });
                                builder.show();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(boardUpdate.this);
                                builder.setMessage("수정에 실패하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                builder.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                boardUpdateRequest boardRequest  = new boardUpdateRequest(userID, getBbsID, bbsTitle, bbsContent, responseListener);
                RequestQueue queue = Volley.newRequestQueue(boardUpdate.this); //큐로 보낸다.
                queue.add(boardRequest);
            }
        });
    }
}