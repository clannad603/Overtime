package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
private List<Fruit>fruitList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
            recyclerView.setAdapter(adapter);
        Button forceoffline=(Button)findViewById(R.id.force_offline);
        forceoffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
    private void  initFruits(){
        for (int i = 0; i < 1; i++) {
            Fruit apple=new Fruit("1",R.drawable.er);
            fruitList.add(apple);
            Fruit a=new Fruit("2",R.drawable.apple);
            fruitList.add(a);
            Fruit b=new Fruit("3",R.drawable.black);
            fruitList.add(b);
            Fruit c=new Fruit("4",R.drawable.grap);
            fruitList.add(c);
            Fruit d=new Fruit("5",R.drawable.lemon);
            fruitList.add(d);
            Fruit e=new Fruit("6",R.drawable.liu);
            fruitList.add(e);
            Fruit f=new Fruit("7",R.drawable.watermelon);
            fruitList.add(f);
            Fruit g=new Fruit("8",R.drawable.xer);
            fruitList.add(g);
            Fruit h=new Fruit("9",R.drawable.or);
            fruitList.add(h);
            Fruit j=new Fruit("10",R.drawable.per);
            fruitList.add(j);
        }
    }
}