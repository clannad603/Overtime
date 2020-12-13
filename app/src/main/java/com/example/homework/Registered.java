package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Registered extends BaseActivity {
    private EditText account,password1,spassword;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ImageButton button2 = (ImageButton) findViewById(R.id.ImageButton_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registered.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        Button set = (Button) findViewById(R.id.Button_rset);
        account = (EditText) findViewById(R.id.EditText_account1);
        password1 = (EditText) findViewById(R.id.EditText_r0password);
        spassword = (EditText) findViewById(R.id.EditText_r1password);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MHandler mHandler=new MHandler();
                HashMap<String, String> map = new HashMap<>();
                map.put("username", account.getText().toString());
                map.put("password",password1.getText().toString());
                map.put("repassword",spassword.getText().toString());
             sendPostNetRequest("https://www.wanandroid.com/user/register", map,mHandler);

              finish();
            }
     });
   }
    private class MHandler extends Handler {
        @Override public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String errorMsg= msg.obj.toString();
            Toast.makeText(Registered.this,errorMsg,Toast.LENGTH_SHORT).show();
        }
    }
    private void sendPostNetRequest(String mUrl, HashMap<String, String> params,MHandler mHandler) {
        new Thread( () -> { try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            StringBuilder dataToWrite = new StringBuilder();
            for (String key : params.keySet()) { dataToWrite.append(key).append("=").append(params.get(key)).append("&");
            }
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(dataToWrite.substring(0, dataToWrite.length() - 1).getBytes());
            InputStream in = connection.getInputStream();
            String responseData = StreamToString(in);
                JSONObject jsonObject=new JSONObject(responseData);
            int errorCode=jsonObject.getInt("errorCode");
            String errorMsg=jsonObject.getString("errorMsg");
          if(errorCode!=0){
              Message message = new Message();
              message.obj = errorMsg;
              mHandler.sendMessage(message);
//               JSONObject jsonObject1=jsonObject.getJSONObject("data");
//               String name=jsonObject1.getString("username");
//              String password=jsonObject1.getString("password");
          }

        } catch (Exception e) { e.printStackTrace(); } } ).start(); }
    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        String oneLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while ((oneLine = reader.readLine()) != null) {
                sb.append(oneLine).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace(); }
        }

        return sb.toString();
    }


}
//                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                editor.putString("name", account.getText().toString());
//                SQLiteDatabase db=dbHelper.getWritableDatabase();
//                ContentValues values=new ContentValues();
//                values.put("user",account.getText().toString());
//                if(password1.getText().toString().equals(spassword.getText().toString())){
//                    values.put("password",password1.getText().toString());
//                    db.insert("User",null,values);
//                    values.clear();
//                editor.putString("password", password1.getText().toString());
//                    }
//                else{
//                    Toast.makeText(Registered.this,"两次输入不一致",Toast.LENGTH_SHORT).show();
//                }
//                editor.apply();