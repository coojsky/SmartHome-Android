package com.example.a212.project.flagment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a212.project.R;
import com.example.a212.project.activity.MainActivity;
import com.example.a212.project.adapter.LogListAdapter;
import com.example.a212.project.object.log;

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
import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserFragment() {
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
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

    private TextView userIDtv;
    private TextView userNametv;
    private TextView userGendertv;
    private TextView userEmailtv;
    private Button userUpdate;

    private ListView LogListView;
    private LogListAdapter logadapter;
    private List<log> LogList;



    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);

        //해당 리스트뷰와 일치시킨다.
        userIDtv = (TextView) getView().findViewById(R.id.userID);
        userNametv = (TextView) getView().findViewById(R.id.userName);
        userGendertv = (TextView) getView().findViewById(R.id.userGender);
        userEmailtv = (TextView) getView().findViewById(R.id.userEmail);
        userUpdate = (Button) getView().findViewById(R.id.userUpdate);

        userUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new UserUpdateFragment());
                fragmentTransaction.commit();

            }
        });

        LogListView = (ListView) getView().findViewById(R.id.LogListView);
        LogList = new ArrayList<log>();
        logadapter= new LogListAdapter(getContext().getApplicationContext(), LogList, this);
        LogListView.setAdapter(logadapter);


        new BackgroundTask().execute();
        new LogBackgroundTask().execute();



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
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
                String UserGender = "";
                String UserEmail = "";

                    JSONObject object = jsonArray.getJSONObject(count); //현재의 배열의 원소값을 가져온다.
                    //파싱된 데이터를 넣는다.
                    UserID = object.getString("userID");
                    UserName = object.getString("userName");
                    UserGender = object.getString("userGender");
                    UserEmail = object.getString("userEmail");

                userIDtv.setText(UserID);
                userNametv.setText(UserName);
                userGendertv.setText(UserGender);
                userEmailtv.setText(UserEmail);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    class LogBackgroundTask extends AsyncTask<Void,Void,String>      //207번째줄 오류
    {
        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://220.66.87.212:3335/LogList.php";

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
                LogList.clear();//강의 목록을 없애준다. 다른것을 했다가 들어오는 경우
                JSONObject jsonObject = new JSONObject(result); //파싱을 통해서 작동
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                String Room;
                String Tech;
                String Switch;
                String UserID;
                String Date;

                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count); //현재의 배열의 원소값을 가져온다.
                    //파싱된 데이터를 넣는다.
                    Room = object.getString("Room");
                    Tech = object.getString("Tech");
                    Switch = object.getString("Switch");
                    UserID = object.getString("UserID");
                    Date = object.getString("Date");
                    log log = new log(Room,Tech,Switch,UserID,Date);
                    LogList.add(log);
                    count++;

                }
                logadapter.notifyDataSetChanged();//값이 변경 되는 것을 알려줌
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
