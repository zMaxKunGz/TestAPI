package com.maxpasit.testapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_clear)
    Button btn_clear;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_age)
    EditText edt_age;
    private String name;
    private String age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn_submit.setOnClickListener(this);
        btn_clear.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        if(v == btn_submit)
        {
            Toast.makeText(getApplicationContext(), "Submit", Toast.LENGTH_SHORT).show();
            name = edt_name.getText().toString();
            age = edt_age.getText().toString();
            new ATask().execute("http://192.168.1.57/test/index.php");
        }
    }

    private class ATask extends AsyncTask<String, Integer ,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("name", name)
                    .add("age", age)
                    .build();

            Request.Builder builder = new Request.Builder();
            Request request = builder
                    .url("http://192.168.1.57/test/index.php")
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful())
                {
                    return response.body().string();
                }
                else
                {
                    return "Not success";
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return "Not success";
        }
    }
}
