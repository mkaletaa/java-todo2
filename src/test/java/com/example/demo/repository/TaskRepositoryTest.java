package com.example.demo.repository;

import com.example.demo.model.Task;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {
    private Repository<Task> taskRepository = new TaskRepository();

    @Nested
    class TestAdd {
        @Test
        void shouldAddItemWhenNotNull () {
            // given
            Task task = new Task(1, "test", "TEST");
            // when
            taskRepository.add(task);
            // then
            assertEquals(task, taskRepository.get(task.getId()));
        }

        @Test
        void shouldAddItemWhenNull () {
            // given
            Task task = null;

//        try{
//        // when
//        taskRepository.add(task);
//        } catch(IllegalStateException e){
//        // then
//        assertThrows(IllegalStateException.class,()-> taskRepository.add(task));
//        }

            // when, then
            assertThatThrownBy(() -> taskRepository.add(task))
                    .isInstanceOf(IllegalStateException.class);

        }
    }

    @Nested
    class TestGet {
        @Test
        public void shouldGetWhenValidId() {
            //given
            Task task = new Task(1, "test", "TEST");
            //when
            taskRepository.add(task);
            taskRepository.get(1);
            //then
            assertTrue(taskRepository.get(1).equals(task));
        }

        @Test
        public void shouldGetWhenInvalidId() {
            //given
            Task task = new Task(1, "test", "TEST");

//        try{
//        // when
//            taskRepository.add(task);
//            taskRepository.get(2);
//        } catch(IllegalStateException e){
//        // then
//        assertThrows(IllegalStateException.class,()-> taskRepository.get(2));
//        }

            //when
            taskRepository.add(task);
            // then
            assertThatThrownBy(() -> taskRepository.get(2))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Test
    public void shouldGetAllItems() {
        //given
        Task task1 = new Task(1, "Task 1", "Test 1");
        Task task2 = new Task(2, "Task 2", "Test 2");
        Map<Integer, Task> allItems = new HashMap<>();

        //when
        allItems.put(1, task1);
        allItems.put(2, task2);
        taskRepository.add(task1);
        taskRepository.add(task2);

        //then
        assertTrue(taskRepository.getAllItems().equals(allItems));
    }

//    null, wszystkie taski w repozytorium, napisać do tego test, klasa User (id, name, listaTasków)
//    repozytoium usera, klasa TaskService, która dodaje taska do danego usera
//    Hello World controller, API do dodawania i pobierania taska, test rest template

    //testy, wstrzykiwanie zależności, typy generyczne, hashCode()

}