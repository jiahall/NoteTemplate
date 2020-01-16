package android.jia.notetemplate;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

    public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {
        public ArrayList<NoteRow> mNoteList;
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onDeleteClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            mListener = listener;
        }

        public class RowViewHolder extends RecyclerView.ViewHolder{
            public EditText title;
            public EditText body;
            public Button delete;


            public RowViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
                super(itemView);
                title = itemView.findViewById(R.id.edt_rowTitle);

                title.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        mNoteList.get(getAdapterPosition()).setTitle(title.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                body = itemView.findViewById(R.id.edt_rowBody);

                body.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        mNoteList.get(getAdapterPosition()).setBody(body.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                delete= itemView.findViewById(R.id.btn_removeRow);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null){
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION){
                                listener.onDeleteClick(position);
                            }
                        }

                    }
                });

            }
        }
        public RowAdapter(ArrayList<NoteRow> noteList){
            mNoteList = noteList;
        }

        @NonNull
        @Override
        public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
            RowViewHolder nvh = new RowViewHolder(v, mListener);
            return nvh;
        }

        @Override
        public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
            NoteRow currentItem = mNoteList.get(position);

            holder.title.setText(currentItem.getTitle());
            holder.body.setText(currentItem.getBody());


        }

        @Override
        public int getItemCount() {
            return mNoteList.size();
        }

    }

