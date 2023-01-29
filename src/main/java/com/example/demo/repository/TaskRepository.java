package com.example.demo.repository;

import com.example.demo.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class TaskRepository implements Repository<Task>{


    Map<UUID, Task> tasksById;
    public TaskRepository() {
        this.tasksById = new HashMap<UUID, Task>();
    }

    @Override
    public void add(Task item) {
        if(item==null){
            throw new IllegalStateException();
        }

        tasksById.put(item.getId(), item);
    }

    @Override
    public Task getTaskById(UUID id) {
        if(!tasksById.containsKey(id)){
            throw new IllegalStateException();
        }

        return tasksById.get(id);
    }

    @Override
    public Task getTaskByIndex(int nr){
        List<Task> values = tasksById.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());

        if(nr>values.size()){
            throw new IllegalStateException();
        }

        return values.get(nr-1);
    }

    @Override
     public List<Task> getAllItems() {
        return tasksById.values().stream().toList();
    }
}
