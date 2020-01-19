package android.jia.notetemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<NoteItem> mNoteList;
    private RecyclerView mRecyclerview;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;
    Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
          prefEditor = sharedPreferences.edit();

        saveNote();
        preparePrefList();
        buildRecyclerView();
        mAdd = findViewById(R.id.btn_add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mNoteList.add(new NoteItem("YES!", "it worked"));
                mAdapter.notifyItemInserted(mNoteList.size() - 1);
                Gson gson = new Gson();
                String json = gson.toJson(mNoteList);
                prefEditor.putString("NoteList", json);
                prefEditor.apply();*/

                Intent intent = new Intent(getBaseContext(),NoteMaker.class);
                //Jia when you bundle the title and date over you gotta make sure there IS actually a date put a check in
                startActivity(intent);
            }
        });
    }

    private void saveNote() {
        // TODO: 19/01/2020 hey gotta make an if statment to check if there actually is an intent 
        //get data sent over from notemaker class
        Intent intent = getIntent();
        String title = intent.getStringExtra("theTitle");
        String body = intent.getStringExtra("theBody");

        //Finds shared preference string containing note list as JSON
        Gson gson = new Gson();
        String json = sharedPreferences.getString("NoteList","");


        //Converts to arrayList for RecyclerView
        Type type = new TypeToken<List<NoteItem>>(){}.getType();
        mNoteList = gson.fromJson(json, type);

        //add data to arraylist
        mNoteList.add(new NoteItem(title, body));

        String note = gson.toJson(mNoteList);
        prefEditor.putString("NoteList", note);
        prefEditor.apply();
    }

    private void preparePrefList() {
        //Finds shared preference string containing note list as JSON
        Gson gson = new Gson();
        String json = sharedPreferences.getString("NoteList","");


        //Converts to arrayList for RecyclerView
        Type type = new TypeToken<List<NoteItem>>(){}.getType();
        mNoteList = gson.fromJson(json, type);

    }


    public void removeItem(int position){
        mNoteList.remove(position);
        Gson gson = new Gson();
        String json = gson.toJson(mNoteList);
        prefEditor.putString("NoteList", json);
        prefEditor.apply();
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text){
        mNoteList.get(position).changeName(text);
        mAdapter.notifyItemChanged(position);
    }



    public void buildRecyclerView() {
        mRecyclerview = findViewById(R.id.recyclerView);
        mRecyclerview.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mAdapter = new NoteAdapter(mNoteList);

        mRecyclerview.setLayoutManager(mlayoutManager);
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position,"clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }


        });


    }





}
