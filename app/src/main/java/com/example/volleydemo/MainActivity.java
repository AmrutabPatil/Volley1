package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext,

    //button,textview and progressbar

    private EditText nameEdt,jobEdt;
    private TextView responseTV;
    private ProgressBar loadingPB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initializing our views
        nameEdt=findViewById(R.id.idEdtName);
        jobEdt=findViewById(R.id.idEdtJob);
        Button postDataBtn = findViewById(R.id.idBtnPost);
        responseTV=findViewById(R.id.idTVResponse);
        loadingPB=findViewById(R.id.idLoadingPB);


        //adding on click listener to our button

        postDataBtn.setOnClickListener(v -> {
            //validating if the text field is empty or not.

            if(nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this,"Please enter both the values",Toast.LENGTH_SHORT).show();
                return;

            }

            //calling a method to post the data and passing our name and job.

            postDataUsingVolley(nameEdt.getText().toString(),jobEdt.getText().toString());

        });
    }

    private void postDataUsingVolley(String name, String job) {

        //url to post our data

        String url="https://reqres.in/api/users";
        loadingPB.setVisibility(View.VISIBLE);

        //creating a new variable for our request queue

        RequestQueue Queue= Volley.newRequestQueue(MainActivity.this);


        StringRequest request=new StringRequest(Request.Method.POST,url, response -> {



            loadingPB.setVisibility(View.GONE);
            nameEdt.setText("");
            jobEdt.setText("");

            Toast.makeText(MainActivity.this,"Data added to API",Toast.LENGTH_SHORT).show();
            try{

                JSONObject respObj=new JSONObject(response);

                String name1 = respObj.getString("name");
                String job1 = respObj.getString("job");

                responseTV.setText(String.format("Name: %s\nJob :%s", name1, job1));

            }catch(JSONException e){
                e.printStackTrace();

            }
        }, error -> Toast.makeText(MainActivity.this,"Fail to get response=" + error, Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String,String> getParams(){


                Map<String,String>params= new HashMap<>();

                params.put("name",name);
                params.put("job",job);

                return params;


            }

        };
        Queue.add(request);


    }
}