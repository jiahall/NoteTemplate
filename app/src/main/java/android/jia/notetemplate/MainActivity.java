package android.jia.notetemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<NoteItem> mNoteList;
    private RecyclerView mRecyclerview;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNoteList();
        buildRecyclerView();
    }

    public void removeItem(int position){
        mNoteList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text){
        mNoteList.get(position).changeName(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createNoteList() {
        mNoteList = new ArrayList<>();
        mNoteList.add(new NoteItem("hello1","test\nboii\n1"));
        mNoteList.add(new NoteItem("hello2","test\nboii\n2"));
        mNoteList.add(new NoteItem("hello3","test\nboii\n3"));
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
