package edu.neu.khoury.madsea.chihweilo;

import android.graphics.Color;
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
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }



    private ArrayList<TaskDetails> mTodoList;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mChecked;
        public TextView mTitle;
        public TextView mDeadline;

        public RecyclerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mChecked = itemView.findViewById(R.id.checkboxView);
            mTitle = itemView.findViewById(R.id.textViewTitle);
            mDeadline = itemView.findViewById(R.id.textViewDeadline);
            mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            itemView.setBackgroundColor(Color.GRAY);
                        } else {
                            itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                    }
                }
            );

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerViewAdapter(ArrayList<TaskDetails> todolist) {
        mTodoList = todolist;
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
        TaskDetails currentItem = mTodoList.get(position);

        holder.mChecked.setChecked(false);
        holder.mTitle.setText(currentItem.getTitle());
        Date deadline = currentItem.getDateDeadline();
        if (deadline != null) {
            holder.mDeadline.setText("By " + inputDateFormat.format(currentItem.getDateDeadline()));
        } else {
            holder.mDeadline.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }
}
