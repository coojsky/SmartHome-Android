package com.example.a212.project.flagment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a212.project.R;
import com.example.a212.project.adapter.TechListAdapter;
import com.example.a212.project.adapter.TemporateListAdapter;
import com.example.a212.project.object.Tech;

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
import java.util.Calendar;
import java.util.List;


//강으 ㅣ목록 액티비티

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TechFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TechFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TechFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TechFragment() {
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
    public static TechFragment newInstance(String param1, String param2) {
        TechFragment fragment = new TechFragment();
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

    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;

    //강의를 보이게 하는데 사용할 변수
    private ListView techListView;
    private TechListAdapter adapter;
    private List<Tech> techList;

    private ListView temListView;
    private TemporateListAdapter temadapter;
    private List<Tech> temList;

    private TextView Date;

    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);



        majorSpinner = (Spinner)getView().findViewById(R.id.majorSpinner);
        majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);
                //선택된 버튼을 가져온다

        //해당 리스트뷰와 일치시킨다.
        techListView = (ListView) getView().findViewById(R.id.courseListView);
        techList = new ArrayList<Tech>();
        adapter= new TechListAdapter(getContext().getApplicationContext(), techList, this);
        techListView.setAdapter(adapter);

        temListView = (ListView) getView().findViewById(R.id.temperateListView);
        temList = new ArrayList<Tech>();
        temadapter= new TemporateListAdapter(getContext().getApplicationContext(), temList, this);
        temListView.setAdapter(temadapter);

        Date = (TextView) getView().findViewById(R.id.Date);



        //버튼을 일치시키고 버튼에 이벤트를 넣는다.
        Button searchButton = (Button)getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                final int year = now.get(Calendar.YEAR);
                final int month = now.get(Calendar.MONTH)+1;
                final int date = now.get(Calendar.DATE);
                final int hour = now.get(Calendar.HOUR);
                final int min = now.get(Calendar.MINUTE);
                final int sec = now.get(Calendar.SECOND);
                Date.setText(year +"년 " + month +"월 " + date + "일 " + hour + "시 " + min + "분 " + sec + "초 ");
                new BackgroundTask().execute();
                new TemBackgroundTask().execute();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tech, container, false);
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
                target = "http://220.66.87.212:3335/CourseList2.php?courseMajor=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8");

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
                techList.clear();//강의 목록을 없애준다. 다른것을 했다가 들어오는 경우
                JSONObject jsonObject = new JSONObject(result); //파싱을 통해서 작동
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String Room;
                String Tech;
                String Switch;
                while(count< jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count); //현재의 배열의 원소값을 가져온다.
                    //파싱된 데이터를 넣는다.
                    Room = object.getString("Room");
                    Tech = object.getString("Tech");
                    Switch = object.getString("Switch");
                    Tech tech = new Tech(Room,Tech,Switch);
                    techList.add(tech);
                    count++;
                }

                adapter.notifyDataSetChanged();//값이 변경 되는 것을 알려줌
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    class TemBackgroundTask extends AsyncTask<Void,Void,String>      //207번째줄 오류
    {
        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://220.66.87.212:3335/TemList2.php?courseMajor=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8");

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
                temList.clear();//강의 목록을 없애준다. 다른것을 했다가 들어오는 경우
                JSONObject jsonObject = new JSONObject(result); //파싱을 통해서 작동
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String Room;
                String Tech;
                String Switch;
                while(count< jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count); //현재의 배열의 원소값을 가져온다.
                    //파싱된 데이터를 넣는다.
                    Room = object.getString("Room");
                    Tech = object.getString("Tech");
                    Switch = object.getString("Switch");
                    Tech tech = new Tech(Room,Tech,Switch);
                    temList.add(tech);
                    count++;
                }
                temadapter.notifyDataSetChanged();//값이 변경 되는 것을 알려줌
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
