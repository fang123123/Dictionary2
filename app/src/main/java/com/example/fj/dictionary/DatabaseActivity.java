package com.example.fj.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fj.dictionary.Words.WordsContent;

public class DatabaseActivity extends  Activity  implements ItemFragment.OnListFragmentInteractionListener,BlankFragment.OnFragmentInteractionListener{
    private Button Rebutton;
    protected void onCreate(Bundle savedInstanceState) {
        WordsContent.searchDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Rebutton=(Button)findViewById(R.id.Rebutton);

        Rebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DatabaseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onFragmentInteraction(String id) {
        Bundle arguments =new Bundle();
        arguments.putString("id",id);
        BlankFragment fragment=new BlankFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(R.id.worddetail, fragment).commit();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
    public void onListFragmentInteraction(WordsContent.WordsItem item)
    {
        onFragmentInteraction(item.content);
    }
}