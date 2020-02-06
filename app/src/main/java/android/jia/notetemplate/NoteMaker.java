package android.jia.notetemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
        setButtons();
        intentCheck();
        buildRecyclerView();
    }

    private void setButtons() {
        mCancel = findViewById(R.id.btn_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        mAddLine = findViewById(R.id.btn_addLine);
        mAddLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adds new note row
                mNoteRow.add(new NoteRow("",""));
                mRowAdapter.notifyItemInserted(mNoteRow.size() -1);
            }
        });

        mSave = findViewById(R.id.btn_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //makes sure data is in correct
                if(checkData()) {
                    //then transforms list into string
                    StringBuilder s = new StringBuilder();
                    for (int i = 0; i < mNoteRow.size(); i++) {
                        //makes sure the title is not empty
                        if(!mNoteRow.get(i).getTitle().trim().equals("")) {
                            s.append(mNoteRow.get(i).getTitle());
                            s.append(": ");
                            s.append(mNoteRow.get(i).getBody());
                            s.append("\n");

                        }
                    }
                    //removes last empty line
                    s.delete(s.length()-1,s.length());
                    //move data to main class to be added to sharedpref
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("theTitle", mNoteRow.get(0).getBody());
                    intent.putExtra("theBody", s.toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void intentCheck() {
        mNoteRow = new ArrayList<NoteRow>();
            //check intent for possible note to be edited
            if(getIntent()!=null && getIntent().getExtras()!=null){
                Bundle bundle = getIntent().getExtras();
                if(bundle.getString("editNote") != null){
                    prepareNoteList(bundle.getString("editNote"));
                    }
                }else{
                    prepareNoteList();
                }
            }

    private boolean checkData() {
        //makes sure title is not empty
        if(mNoteRow.get(0).getBody().equals("")){
            Toast.makeText(NoteMaker.this, "Please enter something in the title space", Toast.LENGTH_SHORT).show();
            return false;
            //makes sure name is not already in use
        }else if(!nameCheck()){
            Toast.makeText(this, "That name is already in use", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean nameCheck() {
        //makes sure name is not already in use
        List<NoteItem> list = MainActivity.getNoteList();
        for(int i = 0; i<list.size(); i++){
            if(mNoteRow.get(0).getBody().toLowerCase().trim()
                    .equals(list.get(i).getName().toLowerCase().trim())){
                return false;
            }
        }
        return true;
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
        //for new note
        Date currentTime = Calendar.getInstance().getTime();
        mNoteRow.add(new NoteRow("Title",""));
        mNoteRow.add(new NoteRow("Date",currentTime.toString()));

    }

    private void prepareNoteList(String list) {
        //for editing note
        Scanner scanner = new Scanner(list);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int posA = line.indexOf(":");
            if (posA == -1) {
                Toast.makeText(NoteMaker.this, "Please refrain from using (:) in the note", Toast.LENGTH_SHORT).show();
            }
            String b = line.substring(0, posA);
            String c = line.substring(posA + 2);
            mNoteRow.add(new NoteRow(b, c));
        }
    }
    public void removeItem(int position){
        mNoteRow.remove(position);
        mRowAdapter.notifyItemRemoved(position);

    }

}
