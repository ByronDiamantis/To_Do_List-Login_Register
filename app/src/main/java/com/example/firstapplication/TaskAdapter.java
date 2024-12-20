package com.example.firstapplication;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.example.firstapplication.sampledata.TaskRepository;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final ArrayList<String> tasks;

    private TaskRepository taskRepo;
    private SQLiteDatabase db;

    public TaskAdapter(ArrayList<String> tasks, TaskAdapter taskAdapter) {
        this.tasks = tasks;
        this.taskRepo = taskRepo;
    }

    //Creates a new TaskViewHolder instance to represent a single list item.
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    //Binds data from the tasks list to the views in the TaskViewHolder
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        db = new DatabaseHelper(this).getWritableDatabase();
        taskRepo = new TaskRepository(db);
        String task = tasks.get(position);
        holder.taskTextView.setText(task);

        // Set delete button functionality
        holder.deleteButton.setOnClickListener(v -> {
            taskRepo.deleteTask(task); // Delete from database
            tasks.remove(position); // Remove from list
            notifyItemRemoved(position); // Notify adapter
        });
    }

    //Returns the total number of items in the tasks list. This tells the RecyclerView how many items to display.
    @Override
    public int getItemCount() {
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