package com.example.firstapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.example.firstapplication.Models.Task;
import com.example.firstapplication.R;
import com.example.firstapplication.Services.TaskService;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final ArrayList<Task> tasks;
    private final TaskService taskService;
    public final int userId;

    public TaskAdapter(ArrayList<Task> tasks, TaskService taskService, int userId) {
        this.tasks = tasks != null ? tasks : new ArrayList<>(); // Initialize to an empty list if null
        this.taskService = taskService;
        this.userId = userId; // Initialize userId
    }

    // Creates a new TaskViewHolder instance to represent a single list item.
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delete_task, parent, false);
        return new TaskViewHolder(view);
    }

    // Binds data from the tasks list to the views in the TaskViewHolder
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskTextView.setText(task.getName());

        holder.deleteButton.setOnClickListener(v -> {
            if (taskService.deleteTask(task.getName())) { // Task successfully deleted
                tasks.remove(position); // Remove task from the list
                notifyItemRemoved(position); // Notify adapter
            } else {
                Toast.makeText(holder.itemView.getContext(), "Failed to delete task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Returns the total number of items in the tasks list. This tells the RecyclerView how many items to display.
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