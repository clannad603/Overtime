package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
                String account=accountEdit.getText().toString();//get the edit
                String password=passwordEdit.getText().toString();
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
             SharedPreferences pre = getSharedPreferences("data", MODE_PRIVATE);
             if(account.equals(pre.getString("name", "10086"))&&password.equals(pre.getString("password", "123456"))){
                    editor=pref.edit();
                    if(rememberPass.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"密码或账号无效",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}