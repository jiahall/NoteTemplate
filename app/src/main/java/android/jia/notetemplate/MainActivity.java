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

        //getPrefList();
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

    private void getPrefList() {

        ArrayList<NoteItem>mNoteListX = new ArrayList<>();
        mNoteListX.add(new NoteItem("hello1","test\nboii\n1"));
        mNoteListX.add(new NoteItem("hello2","test\nboii\n2"));
        mNoteListX.add(new NoteItem("hello3","test\nboii\n3"));


        Gson gson = new Gson();
        String json = gson.toJson(mNoteListX);
        prefEditor.putString("NoteList", json);
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
