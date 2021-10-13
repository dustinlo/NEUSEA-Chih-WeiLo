package edu.neu.khoury.madsea.chihweilo;

import android.content.Context;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private OnItemClickListener mListener;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private List<ToDoItem> toDoItemList;

    public RecyclerViewAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    public void setToDoList(List<ToDoItem> todolist) {
        this.toDoItemList = todolist;
        notifyDataSetChanged();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mChecked;
        public TextView mTitle;
        public TextView mDeadline;

        public RecyclerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mChecked = itemView.findViewById(R.id.checkboxView);
            mTitle = itemView.findViewById(R.id.textViewTitle);
            mDeadline = itemView.findViewById(R.id.textViewDeadline);
        }
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
        ToDoItem currentToDo = toDoItemList.get(holder.getAdapterPosition());
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("POSITION IS " + holder.getAdapterPosition());
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
                    mListener.checkToDo(toDoItemList.get(holder.getAdapterPosition()), isChecked);
                }
            });
    }

    @Override
    public int getItemCount() {
        if(toDoItemList == null || toDoItemList.size() == 0)
            return 0;
        else
            return toDoItemList.size();
    }

    public interface OnItemClickListener {
        void editToDo(int position);
        void checkToDo(ToDoItem todo, boolean isChecked);
    }
}
