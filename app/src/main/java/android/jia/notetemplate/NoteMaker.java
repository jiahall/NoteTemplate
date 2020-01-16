package android.jia.notetemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteMaker extends AppCompatActivity {
    private Button mSave, mCancel;
    private Button mAddLine;
    private ArrayList<NoteRow> mNoteRow;
    private RecyclerView mRecyclerview;
    private RowAdapter mRowAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_maker);


        mSave = findViewById(R.id.btn_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String body = mNoteRow.get(2).getBody().toString();
                Toast.makeText(NoteMaker.this, "well it should be fart"+body, Toast.LENGTH_SHORT).show();
            }
        });
        mAddLine = findViewById(R.id.btn_addLine);
        mAddLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoteRow.add(new NoteRow("",""));
                mRowAdapter.notifyItemInserted(mNoteRow.size() -1);
            }
        });

        prepareNoteList();
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        mRecyclerview = findViewById(R.id.recyclerView);
        mRecyclerview.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mRowAdapter = new RowAdapter(mNoteRow);

        mRecyclerview.setLayoutManager(mlayoutManager);
        mRecyclerview.setAdapter(mRowAdapter);

        mRowAdapter.setOnItemClickListener(new RowAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    private void prepareNoteList() {
        mNoteRow = new ArrayList<NoteRow>();
        mNoteRow.add(new NoteRow("Title",""));
        mNoteRow.add(new NoteRow("Date","125"));

    }
    public void removeItem(int position){
        mNoteRow.remove(position);
        mRowAdapter.notifyItemRemoved(position);

    }
//HI JIA GOTTA MAKE EACH NOTE LIST THING A LIL LINEAR LIST ID i WITH THE EDITtEXT ID iA iB iA iB ADD THE FIRST ONE IN WITH 0A 0B THEN MAKE THE BUTTON CREAT IT AND WITH A COUNT VARIABLE AND WHEN YOU MAKE
    //A NEW ONE IT'LL BE COUNT +1 THEN YOU CAN LOOP THROUGH THAT ADDING ":" TO THE ARRAY AND IT'LL ALSO BE USED TO SPLIT THEM AND A /N TO SIGNIFY AN NEW LINE AND WHEN TO MAKE A NEW LINEARLAYOUT
    //THEN SAVE IT THE THE MAIN ARRAY LIST THEN ADD AN EDIT TEXT FOR DATE AND ADD THE NAME JUST TURN IT INTO AND OBJECT AND BUNDLE IT INTO THE MAIN ACTIVITY IF YOU CANT JUSTT ADD IT
    //THEN MAKE SURE YOU ADD AN INTENT SO IT CAN BE SENT EVERYWHERE THEN MAKE IT ALL LOOK PRETRTY AND YOU'RE DONE

}
