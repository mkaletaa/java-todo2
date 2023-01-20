package com.example.demo.repository;

import com.example.demo.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {
    private Repository<Task> taskRepository = new TaskRepository();

    @Test
    void shouldAddItemWhenNotNull() {
        // given
        Task task = new Task(1, "test", "TEST");
        // when
        taskRepository.add(task);
        // then
        assertEquals(task, taskRepository.get(task.getId()));
    }
//    null, wszystkie taski w repozytorium, napisać do tego test, klasa User (id, name, listaTasków)
//    repozytoium usera, klasa TaskService, która dodaje taska do danego usera
//    Hello World controller, API do dodawania i pobierania taska, test rest template

}