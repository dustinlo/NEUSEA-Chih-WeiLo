package edu.neu.khoury.madsea.chihweilo;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private OnToDoListener mListener;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private List<ToDoItem> mToDos;


    public RecyclerViewAdapter(List<ToDoItem> todos, OnToDoListener listener) {
        this.mToDos = todos;
        this.mListener = listener;
    }




    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(v, mListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        try {
            ToDoItem currentToDo = mToDos.get(position);
            holder.mTitle.setText(currentToDo.getTitle());
            holder.mChecked.setChecked(currentToDo.isChecked());

            Date deadline;
            try{
                deadline = inputDateFormat.parse(currentToDo.getDateDeadline());
            } catch (Exception e) {
                deadline = null;
            }
            if (deadline != null) {
                holder.mDeadline.setText("By " + inputDateFormat.format(deadline));
            } else {
                holder.mDeadline.setText("");
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage() );
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editToDo(holder.getAdapterPosition());
            }
        });

        holder.mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        holder.itemView.setBackgroundColor(Color.GRAY);
                    } else {
                        holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                    mListener.checkToDo(holder.getAdapterPosition(), isChecked);
                }
            });
    }

    @Override
    public int getItemCount() {
        if(mToDos == null || mToDos.size() == 0)
            return 0;
        else
            return mToDos.size();
    }

    // inner class
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        public CheckBox mChecked;
        public TextView mTitle;
        public TextView mDeadline;

        public RecyclerViewHolder(@NonNull View itemView, OnToDoListener listener) {
            super(itemView);
            mChecked = itemView.findViewById(R.id.checkboxView);
            mTitle = itemView.findViewById(R.id.textViewTitle);
            mDeadline = itemView.findViewById(R.id.textViewDeadline);

            itemView.setOnClickListener(this);
            mChecked.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.editToDo(getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mListener.checkToDo(getAdapterPosition(), isChecked);
        }
    }
    // end of inner class

    public interface OnToDoListener {
        void editToDo(int position);
        void checkToDo(int position, boolean isChecked);
    }
}
