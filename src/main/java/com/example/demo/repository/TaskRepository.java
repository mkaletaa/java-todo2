package com.example.demo.repository;

import com.example.demo.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
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
        if(!tasksById.containsKey(id)){
            throw new IllegalStateException();
        }

        return tasksById.get(id);
    }
    @Override
     public List<Task> getAllItems() {
        return tasksById.values().stream().toList();
    }
}
