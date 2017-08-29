package com.example.a212.project.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 212 on 2017-05-30.
 */

public class ValidateRequest extends StringRequest {

    //회원가입이 가능한지 체크
    final static private String URL = "http://220.66.87.212:3335/UserValidate2.php";
    private Map<String,String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        //각각의 값들을 파라미터로 매칭
        parameters.put("userID", userID);
    }

    @Override
    public Map<String,String> getParams() {
        return parameters;
    }

}
