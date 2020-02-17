package android.jia.notetemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<NoteItem> NoteList;
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

        //check intent f or possible created note
          checkIntent();
          setButtons();
        preparePrefList();
        buildRecyclerView();

    }

    private void setButtons() {
        mAdd = findViewById(R.id.btn_add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*NoteList.add(new NoteItem("YES!", "it worked"));
                mAdapter.notifyItemInserted(NoteList.size() - 1);
                Gson gson = new Gson();
                String json = gson.toJson(NoteList);
                prefEditor.putString("NoteList", json);
                prefEditor.apply();*/

                Intent intent = new Intent(getBaseContext(),NoteMaker.class);
                //Jia when you bundle the title and date over you gotta make sure there IS actually a date put a check in
                startActivity(intent);
            }
        });
    }

    private void checkIntent() {
        //check intent for possible created note
        if(getIntent()!=null && getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("theTitle") != null && bundle.getString("theBody") != null){

                //get data sent over from notemaker class
                String title = bundle.getString("theTitle");
                String body = bundle.getString("theBody");
                //Save note to sharedPref
                saveNote(title, body);
            }
        }
    }

    private void saveNote(String title, String body) {
        //Finds shared preference string containing note list as JSON
        Gson gson = new Gson();
        String json = sharedPreferences.getString("NoteList","");


        //Converts to arrayList for RecyclerView
        Type type = new TypeToken<List<NoteItem>>(){}.getType();
        NoteList = gson.fromJson(json, type);

        //add data to arraylist
        if (NoteList != null) {
            NoteList.add(new NoteItem(title, body));
        }

        //converts arraylist to json string and saves to shared preferences
        String note = gson.toJson(NoteList);
        prefEditor.putString("NoteList", note);
        prefEditor.apply();
    }

    private void preparePrefList() {
        //Finds shared preference string containing note list as JSON
        Gson gson = new Gson();
        String json = sharedPreferences.getString("NoteList","");


        //Converts to arrayList for RecyclerView
        Type type = new TypeToken<List<NoteItem>>(){}.getType();
        NoteList = gson.fromJson(json, type);

    }


    public void removeItem(int position){
        NoteList.remove(position);
        Gson gson = new Gson();
        String json = gson.toJson(NoteList);
        prefEditor.putString("NoteList", json);
        prefEditor.apply();
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text){
        NoteList.get(position).changeName(text);
        mAdapter.notifyItemChanged(position);
    }

    public void editItem(int position){
        String editNote = NoteList.get(position).getBody();
        Intent intent = new Intent(getBaseContext(), NoteMaker.class);
        intent.putExtra("editNote", editNote);
        startActivity(intent);
    }

    public void useItem(int position){
        String data = NoteList.get(position).getBody();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Send message");
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Send intent to");
        startActivity(shareIntent);
    }


    public void buildRecyclerView() {
        mRecyclerview = findViewById(R.id.recyclerView);
        mRecyclerview.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mAdapter = new NoteAdapter(NoteList);

        mRecyclerview.setLayoutManager(mlayoutManager);
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                useItem(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onEditClick(int position) { editItem(position); }

            @Override
            public void onSendClick(int position) {useItem(position);


            }


        });


    }


    public static List<NoteItem> getNoteList() {
        return NoteList;
    }
}
