package com.example.a212.project.activity;

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
        import com.example.a212.project.request.boardRequest;

        import org.json.JSONObject;

public class board extends AppCompatActivity {
    private static String TAG = "phptest_MainActivity";
    private EditText mEditTextName;
    private EditText mEditTextAddress;
    String userID;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        mEditTextName = (EditText)findViewById(R.id.editText_main_name);
        mEditTextAddress = (EditText)findViewById(R.id.editText_main_address);


        userID = getIntent().getStringExtra("userID");


        Button Update = (Button)findViewById(R.id.button_main_insert);
        Button menu = (Button)findViewById(R.id.button_main_menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),board_main.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bbsTitle = mEditTextName.getText().toString();
                String bbsContent = mEditTextAddress.getText().toString();

                mEditTextName.setText("");
                mEditTextAddress.setText("");
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{ //해당 웹사이트에 접속한 이후, 특정한 응답을 받을수 있게 함
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(board.this);
                                dialog = builder.setMessage("글 등록에 성공하였습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(getApplicationContext(),board_main.class);
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(board.this);
                                dialog = builder.setMessage("글 등록에 실패하였습니다.")
                                        .setNegativeButton("다시시도",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                boardRequest boardRequest  = new boardRequest(userID, bbsTitle, bbsContent, responseListener);
                RequestQueue queue = Volley.newRequestQueue(board.this); //큐로 보낸다.
                queue.add(boardRequest);
            }
        });
    }
}