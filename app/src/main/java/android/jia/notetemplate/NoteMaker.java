package android.jia.notetemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteMaker extends AppCompatActivity {
    private Button mSave;
    private Button mAddLine;
    private LinearLayout mLinList, mLinRow;
    private ArrayList<EditText> editTextList = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_maker);

        mLinRow = findViewById(R.id.lin_row);
        mLinList= findViewById(R.id.lin_list);
        mSave = findViewById(R.id.btn_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mAddLine = findViewById(R.id.btn_addLine);
        mAddLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinList.addView(addTextView());
                mLinList.addView(findViewById(R.id.lin_row).setI);


            }
        });
    }
//HI JIA GOTTA MAKE EACH NOTE LIST THING A LIL LINEAR LIST ID i WITH THE EDITtEXT ID iA iB iA iB ADD THE FIRST ONE IN WITH 0A 0B THEN MAKE THE BUTTON CREAT IT AND WITH A COUNT VARIABLE AND WHEN YOU MAKE
    //A NEW ONE IT'LL BE COUNT +1 THEN YOU CAN LOOP THROUGH THAT ADDING ":" TO THE ARRAY AND IT'LL ALSO BE USED TO SPLIT THEM AND A /N TO SIGNIFY AN NEW LINE AND WHEN TO MAKE A NEW LINEARLAYOUT
    //THEN SAVE IT THE THE MAIN ARRAY LIST THEN ADD AN EDIT TEXT FOR DATE AND ADD THE NAME JUST TURN IT INTO AND OBJECT AND BUNDLE IT INTO THE MAIN ACTIVITY IF YOU CANT JUSTT ADD IT
    //THEN MAKE SURE YOU ADD AN INTENT SO IT CAN BE SENT EVERYWHERE THEN MAKE IT ALL LOOK PRETRTY AND YOU'RE DONE
    private void Collect() {
        String get = "";
        ViewGroup group = (ViewGroup)findViewById(R.id.lin_list);
        for(int i = 0, count = group.getChildCount(); i < count; i++){
            View view = group.getChildAt(i);
            if(view instanceof EditText){
                get+=(((EditText)view).getText().toString());
            }
        }
        Log.i("JIA HERE", get);
    }

    private EditText addTextView(){



        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setText("fart");
        editText.setTextSize(32);
        return editText;
    }

}
