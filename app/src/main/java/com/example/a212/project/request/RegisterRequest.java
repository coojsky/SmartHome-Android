package com.example.a212.project.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 212 on 2017-05-30.
 */

public class RegisterRequest extends StringRequest {

    //UserRegister.php에 요청을 보내서 회원가입 시킴
    final static private String URL = "http://220.66.87.212:3335/UserRegister2.php";
    private Map<String,String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender,String username ,String userEmail, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        //각각의 값들을 파라미터로 매칭
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", username);
        parameters.put("userGender", userGender);
        parameters.put("userEmail",userEmail);
    }

    @Override
    public Map<String,String> getParams() {
        return parameters;
    }

}
