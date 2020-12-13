package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class LoginActivity extends BaseActivity {
private EditText accountEdit;
private EditText passwordEdit;
private Button login;
private SharedPreferences pref;
private SharedPreferences.Editor editor;
private CheckBox rememberPass;
 private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button1=(Button) findViewById(R.id.Button_set);
     button1.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View v) {
         Intent intent =new Intent(LoginActivity.this,Registered.class);
             startActivity(intent);
         }
    });
        ImageButton button2=(ImageButton) findViewById(R.id.ImageButton_wei);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"腾讯微博不是凉了吗",Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton button3=(ImageButton) findViewById(R.id.ImageButton_tx);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(LoginActivity.this,"我还没下微博呢",Toast.LENGTH_SHORT).show();
            }
        });
        rememberPass=(CheckBox) findViewById(R.id.remember_pass);
        pref=getSharedPreferences("login",MODE_PRIVATE);
        accountEdit=(EditText)findViewById(R.id.EditText_account);
        passwordEdit=(EditText)findViewById(R.id.EditText_password);
        login=(Button)findViewById(R.id.Button_login);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MHandler1 mHandler1=new MHandler1();
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                HashMap<String, String> map1 = new HashMap<>();
                map1.put("username", account);
                map1.put("password",password);
                sendPostNetRequest("https://www.wanandroid.com/user/login", map1,mHandler1);

            }
        });
    }
    private class MHandler1 extends Handler {
        @Override public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String responseData= msg.obj.toString();
          try {
              JSONObject jsonObject=new JSONObject(responseData);
              int errorCode=jsonObject.getInt("errorCode");
              String errorMsg=jsonObject.getString("errorMsg");
              JSONObject data=jsonObject.getJSONObject("data");
              String name=data.getString("username");
              String pass=data.getString("password");
              if(errorCode!=0){
                  Toast.makeText(LoginActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
              }else{
                  editor=pref.edit();
                     if(rememberPass.isChecked()){
                     editor.putBoolean("remember_password",true);
                     editor.putString("account",name);
                     editor.putString("password",pass);
                     }else{
                     editor.clear();
                     }
                     editor.apply();
                     Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
              }
          }catch (Exception e){ e.printStackTrace(); }
        }
    }
    public String StreamToString(InputStream in) {
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
    private void sendPostNetRequest(String mUrl, HashMap<String, String> params,MHandler1 mHandler1) {
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
            Message message = new Message();
            message.obj = responseData;
            mHandler1.sendMessage(message);
        } catch (Exception e) { e.printStackTrace(); } } ).start(); }
        }
//                SharedPreferences pre = getSharedPreferences("data", MODE_PRIVATE);
//                SQLiteDatabase db=dbHelper.getWritableDatabase();

// if(account.equals(pre.getString("name", "10086"))&&password.equals(pre.getString("password", "123456"))){
//             @SuppressLint("Recycle") Cursor cursor=db.query("User",null,null,null,null,null,null);
//             if(cursor.moveToFirst()) {
//                 do {
//                     if(account.equals(cursor.getString(cursor.getColumnIndex("user")))&&password.equals(cursor.getString(cursor.getColumnIndex("password")))){
//                         editor=pref.edit();
//                         if(rememberPass.isChecked()){
//                             editor.putBoolean("remember_password",true);
//                             editor.putString("account",account);
//                             editor.putString("password",password);
//                         }else{
//                             editor.clear();
//                         }
//                         editor.apply();
//                         Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                         startActivity(intent);
//                         finish();
//                     }else{
//                         Toast.makeText(LoginActivity.this,"密码或账号无效",Toast.LENGTH_SHORT).show();}
//                 }while(cursor.moveToNext());
//             }
//SharedPreferences pre = getSharedPreferences("data", MODE_PRIVATE);
//             if(account.equals(pre.getString("name", "10086"))&&password.equals(pre.getString("password", "123456"))){
//                     editor=pref.edit();
//                     if(rememberPass.isChecked()){
//                     editor.putBoolean("remember_password",true);
//                     editor.putString("account",account);
//                     editor.putString("password",password);
//                     }else{
//                     editor.clear();
//                     }
//                     editor.apply();
//                     Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//        }else{
//        Toast.makeText(LoginActivity.this,"密码或账号无效",Toast.LENGTH_SHORT).show();
//        }