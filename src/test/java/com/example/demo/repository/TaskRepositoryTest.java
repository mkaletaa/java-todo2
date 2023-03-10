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
    private UUID userId = UUID.fromString("181d0c94-ed96-41f9-9f76-8ceaa0ce59c2");

    @Nested
    class TestAdd {
        @Test
        void shouldAddItemWhenNotNull() {
            // given
            UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
            Task task = new Task(task1Id, "test", "TEST", userId);
            // when
            taskRepository.addItem(task);
            // then
            assertEquals(task, taskRepository.getItemById(task.getId()));
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
            assertThatThrownBy(() -> taskRepository.addItem(task))
                    .isInstanceOf(IllegalStateException.class);

        }
    }

    @Nested
    class TestGetSingleTask {

        @Nested
        class TestGetById {
            @Test
            public void shouldGetWhenValidId() {
                //given
                UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
                Task task = new Task(task1Id, "test", "TEST", userId);
                //when
                taskRepository.addItem(task);
                taskRepository.getItemById(task1Id);
                //then
                assertTrue(taskRepository.getItemById(task1Id).equals(task));
            }

            @Test
            public void shouldGetWhenNonExistingId() {
                //given
                UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
                UUID nonExistingId = UUID.fromString("f499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
                Task task = new Task(task1Id, "test", "TEST", userId);
                //when
                taskRepository.addItem(task);
//                taskRepository.getSingleTaskById(nonExistingId);

                // then
                assertThatThrownBy(() -> taskRepository.getItemById(nonExistingId))
                        .isInstanceOf(IllegalStateException.class);
            }

            @Test
            public void shouldGetWhenNull() {
                //given
                UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
                Task task = new Task(task1Id, "test", "TEST", userId);
                //when
                taskRepository.addItem(task);

                // then
                assertThatThrownBy(() -> taskRepository.getItemById(null))
                        .isInstanceOf(IllegalStateException.class);
            }

        }

        @Nested
        class TestGetByIndex{
            @Test
            public void shouldGetWhenValidIndex(){
                //given
                int nr = 1;
                UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
                Task task = new Task(task1Id, "test", "TEST", userId);
                //when
                taskRepository.addItem(task);
                //then
                assertTrue(taskRepository.getItemByIndex(nr).equals(task));
//                assertThatThrownBy(() -> taskRepository.getSingleTask(nr))
//                        .isInstanceOf(IllegalStateException.class);

            }
            @Test
            public void shouldGetWhenInvalidIndex(){
                //given
                int nr = 2;
                UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
                Task task = new Task(task1Id, "test", "TEST", userId);
                //when
                taskRepository.addItem(task);
                //then
                assertThatThrownBy(() -> taskRepository.getItemByIndex(nr))
                        .isInstanceOf(IllegalStateException.class);

            }
        }
    }


    @Test
    public void shouldGetAllItems() {
        //given
        UUID task1Id = UUID.fromString("e499b5df-e341-41c5-bf7a-06bc9c9bc4e9");
        Task task1 = new Task(task1Id, "Task 1", "Test 1", userId);
        UUID task2Id = UUID.fromString("9c80a36a-07a7-4004-81f6-6fa8bcee1d7c");
        Task task2 = new Task(task2Id, "Task 2", "Test 2", userId);
        taskRepository.addItem(task1);
        taskRepository.addItem(task2);

        //when
        List<Task> allItems = taskRepository.getAllItems();

        //then
        assertEquals(2, allItems.size());
        assertTrue(allItems.contains(task1));
        assertTrue(allItems.contains(task2));
    }


}