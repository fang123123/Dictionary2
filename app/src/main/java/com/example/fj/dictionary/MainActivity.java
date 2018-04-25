package com.example.fj.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.databaseHelper.DatabaseStatic;
import com.example.databaseHelper.MyHelper;
import com.example.fj.dictionary.Words.WordsContent;

public class MainActivity extends AppCompatActivity {
    private Button Button1;
    private Button Button2;
    private EditText editText1;
    private static int WordSum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseStatic.myHelper=new MyHelper(this);
        Log.i("123","进入主界面");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button1=(Button)findViewById(R.id.button3);
        Button2=(Button)findViewById(R.id.DTbutton);
        editText1=(EditText)findViewById(R.id.editText1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ResearchActivity.class);
                intent.putExtra("Sum",WordSum);
                startActivityForResult(intent,0);
                Log.i("123","main");
            }
        });
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ResearchActivity.class);
                startActivity(intent);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DatabaseActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,requestCode,data);
        if(requestCode==0&&resultCode==0)
        {
            int sum=data.getIntExtra("Sum1",0);
        }
    }

}
