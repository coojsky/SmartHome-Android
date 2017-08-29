package com.example.a212.project.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 212 on 2017-05-30.
 */

public class AddRequest extends StringRequest {

    final static private String URL = "http://220.66.87.212:3335/CourseAdd2.php";
    private Map<String,String> parameters;
    //특정 사용자가 특정 강의를 들었다는 것을 명시
    public AddRequest(String Switch,String Room,String Tech, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        parameters = new HashMap<>();
        parameters.put("Switch", Switch);
        parameters.put("Room", Room);
        parameters.put("Tech", Tech);

    }

    @Override
    public Map<String,String> getParams() {
        return parameters;
    }

}
