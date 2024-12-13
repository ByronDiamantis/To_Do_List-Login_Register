package com.example.firstapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<String> tasks;
    private DatabaseHelper dbHelper;
    public TaskRepository taskRepository;
    public SQLiteDatabase db;

    public TaskAdapter(ArrayList<String> tasks, DatabaseHelper dbHelper) {
        this.tasks = tasks;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //Creates a new TaskViewHolder instance to represent a single list item.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) { //Binds data from the tasks list to the views in the TaskViewHolder
        String task = tasks.get(position);
        holder.taskTextView.setText(task);

        // Initialize the repository once using the passed dbHelper
        if (taskRepository == null) {
            db = dbHelper.getWritableDatabase();
            taskRepository = new TaskRepository(db);
        }

        // Set delete button functionality
        holder.deleteButton.setOnClickListener(v -> {
            taskRepository.deleteTask(task); // Delete from database
            tasks.remove(position); // Remove from list
            notifyItemRemoved(position); // Notify adapter
        });
    }

    @Override
    public int getItemCount() { //Returns the total number of items in the tasks list. This tells the RecyclerView how many items to display.
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTextView;
        Button deleteButton;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.taskTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}