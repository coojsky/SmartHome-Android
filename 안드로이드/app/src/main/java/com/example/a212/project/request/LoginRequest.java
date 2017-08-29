package com.example.a212.project.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://220.66.87.212:3335/UserLogin2.php";
    private Map<String,String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        //각각의 값들을 파라미터로 매칭
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);

    }

    public Map<String,String> getParams() {
        return parameters;
    }

}

