package com.example.demo.repository;

import com.example.demo.model.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskRepository implements Repository<Task>{


    Map<Integer, Task> tasksById;
    public TaskRepository() {
        this.tasksById = new HashMap<Integer, Task>();
    }

    @Override
    public void add(Task item) {
        if(item==null){
            throw new IllegalStateException();
        }

        tasksById.put(item.getId(), item);
    }

    @Override
    public Task get(int id) {
        return tasksById.get(id);
    }
}
