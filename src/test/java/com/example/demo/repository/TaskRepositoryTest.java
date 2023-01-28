package com.example.demo.repository;

import com.example.demo.model.Task;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {
    private Repository<Task> taskRepository = new TaskRepository();

    @Nested
    class TestAdd {
        @Test
        void shouldAddItemWhenNotNull() {
            // given
            UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
            Task task = new Task(task1Id, "test", "TEST");
            // when
            taskRepository.add(task);
            // then
            assertEquals(task, taskRepository.get(task.getId()));
        }

        @Test
        void shouldAddItemWhenNull() {
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
            UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
            Task task = new Task(task1Id, "test", "TEST");
            //when
            taskRepository.add(task);
            taskRepository.get(task1Id);
            //then
            assertTrue(taskRepository.get(task1Id).equals(task));
        }

        @Test
        public void shouldGetWhenNonExistingId() {
            //given
            UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
            UUID nonExistingId = UUID.fromString("f499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
            Task task = new Task(task1Id, "test", "TEST");
            //when
            taskRepository.add(task);
            // then
            assertThatThrownBy(() -> taskRepository.get(nonExistingId))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        public void shouldGetWhenNull() {
            //given
            UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
            Task task = new Task(task1Id, "test", "TEST");
            //when
            taskRepository.add(task);
            // then
            assertThatThrownBy(() -> taskRepository.get(null))
                    .isInstanceOf(IllegalStateException.class);
        }

    }


    @Test
    public void shouldGetAllItems() {
        //given
        UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
        Task task1 = new Task(task1Id, "Task 1", "Test 1");
        UUID task2Id = UUID.fromString("9c80a36a-07a7-4004-81f6-6fa8bcee1d7c");
        Task task2 = new Task(task2Id, "Task 2", "Test 2");
        taskRepository.add(task1);
        taskRepository.add(task2);

        //when
        List<Task> allItems = taskRepository.getAllItems();

        //then
        assertEquals(2, allItems.size());
        assertTrue(allItems.contains(task1));
        assertTrue(allItems.contains(task2));
    }


}