package com.example.a212.project.flagment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.a212.project.R;
import com.example.a212.project.activity.MainActivity;
import com.example.a212.project.request.UserUpdateRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserUpdateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView userText;
    EditText passwordText;
   EditText passwordText2;
    EditText emailText;
  EditText username2;

    private OnFragmentInteractionListener mListener;

    public UserUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserUpdateFragment newInstance(String param1, String param2) {
        UserUpdateFragment fragment = new UserUpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private AlertDialog dialog;

    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        userText=(TextView) getView().findViewById(R.id.idText);
       passwordText = (EditText) getView().findViewById(R.id.passwordText);
       passwordText2 = (EditText) getView().findViewById(R.id.passwordText2);
       emailText = (EditText) getView().findViewById(R.id.emailText);
       username2 = (EditText) getView().findViewById(R.id.username);

        Button userUpdate = (Button)getView().findViewById(R.id.userUpdateButton);
        userUpdate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String userID = userText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userPassword2 = passwordText2.getText().toString();
                String userEmail = emailText.getText().toString();
                String username = username2.getText().toString();

                if(userID.equals("")|| userPassword.equals("")|| userEmail.equals("") || username.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateFragment.this.getActivity());
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(!userPassword.equals(userPassword2)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateFragment.this.getActivity());
                    dialog = builder.setMessage("패스워드와 패스워드 확인이 틀렸습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{ //해당 웹사이트에 접속한 이후, 특정한 응답을 받을수 있게 함
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateFragment.this.getActivity());
                                dialog = builder.setMessage("회원 등록 정보 변경에 성공하였습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, new UserFragment());
                                fragmentTransaction.commit();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateFragment.this.getActivity());
                                dialog = builder.setMessage("회원 등록 정보 변경에 실패하였습니다.")
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
               UserUpdateRequest userUpdateRequest  = new UserUpdateRequest(userID, userPassword,username, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserUpdateFragment.this.getActivity()); //큐로 보낸다.
                queue.add(userUpdateRequest);

            }
        });

        new BackgroundTask().execute();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userupdate, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>      //207번째줄 오류
    {
        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://220.66.87.212:3335/UserState.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");

            } catch(Exception e){
                e.printStackTrace();
        }
        }
        @Override//안에들어가는kjy
        protected String doInBackground(Void... voids) {
            try{
                URL url=  new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));    //오류내용 233줄
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
            }catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
            try {//파싱을 통해서 강의 목록형태로 보여주는 함수
                JSONObject jsonObject = new JSONObject(result); //파싱을 통해서 작동
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                String UserID = "";
                String UserName = "";
                String UserEmail = "";

                    JSONObject object = jsonArray.getJSONObject(count); //현재의 배열의 원소값을 가져온다.
                    //파싱된 데이터를 넣는다.
                    UserID = object.getString("userID");
                    UserName = object.getString("userName");
                    UserEmail = object.getString("userEmail");

              userText.setText(UserID);
                emailText.setText(UserEmail);
              username2.setText(UserName);



            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


}
