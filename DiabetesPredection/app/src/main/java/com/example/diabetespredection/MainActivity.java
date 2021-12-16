package com.example.diabetespredection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText preg,gluc,bp,st,insulin,bmi,age;
    Button btn;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preg = findViewById(R.id.preg);
        gluc = findViewById(R.id.gluc);
        bp = findViewById(R.id.bp);
        st = findViewById(R.id.st);
        insulin = findViewById(R.id.insulin);
        bmi = findViewById(R.id.bmi);
        age = findViewById(R.id.age);

        btn = findViewById(R.id.btn);
        result = findViewById(R.id.result);

        String url="https://diabetes-predection-backend.herokuapp.com/predict";

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hit api -> volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //successful hit
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ans = jsonObject.getString("diabetes");
                            if(ans.equals("1"))
                                result.setText("You are diabetic, please make an appoinment for a doctor!");
                            else
                                result.setText("You looks fine!");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Error
                        Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){ //passing inputs to post req
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("preg",preg.getText().toString());
                        params.put("gluc",gluc.getText().toString());
                        params.put("bp",bp.getText().toString());
                        params.put("st",st.getText().toString());
                        params.put("insulin",insulin.getText().toString());
                        params.put("bmi",bmi.getText().toString());
                        params.put("age",age.getText().toString());

                        return params;
                    };
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}