package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Registered extends BaseActivity {
    private EditText account,password1,spassword;
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
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("name", account.getText().toString());
                if(password1.getText().toString().equals(spassword.getText().toString())){
                editor.putString("password", password1.getText().toString());}
                else{
                    Toast.makeText(Registered.this,"两次输入不一致",Toast.LENGTH_SHORT).show();
                }
                editor.apply();
                finish();
            }
        });
    }

}