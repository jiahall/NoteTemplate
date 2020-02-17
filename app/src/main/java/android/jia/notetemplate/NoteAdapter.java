package android.jia.notetemplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<NoteItem> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onSendClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView body;
        public Button delete;
        public Button expand;
        public Button use;
        public Button edit;
        public Button contract;
        public LinearLayout layoutOptions;


        public NoteViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            body = itemView.findViewById(R.id.txt_body);
            use = itemView.findViewById(R.id.btnUse);
            edit = itemView.findViewById((R.id.btnEdit));
            delete= itemView.findViewById(R.id.btnDelete);
            expand= itemView.findViewById(R.id.btn_expand);
            contract= itemView.findViewById(R.id.btn_contract);
            layoutOptions= itemView.findViewById(R.id.linearLayout_options);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });

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

            use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<NoteItem> list = MainActivity.getNoteList();
                    if(listener != null){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION){
                            listener.onSendClick(position);
                        }
                    }

                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }

                    }
                }
            });

            expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            body.setVisibility(View.VISIBLE);
                            layoutOptions.setVisibility(View.VISIBLE);
                            expand.setVisibility(View.INVISIBLE);
                            contract.setVisibility(View.VISIBLE);

                        }
                    }

                }
            });

            contract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            body.setVisibility(View.GONE);
                            layoutOptions.setVisibility(View.GONE);
                            expand.setVisibility(View.VISIBLE);
                            contract.setVisibility(View.INVISIBLE);
                        }
                    }

                }
            });



        }
    }
    public NoteAdapter(ArrayList<NoteItem> noteList){
        mNoteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        NoteViewHolder nvh = new NoteViewHolder(v, mListener);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteItem currentItem = mNoteList.get(position);

        holder.name.setText(currentItem.getName());
        holder.body.setText(currentItem.getBody());


    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
