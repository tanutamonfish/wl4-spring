package org.weblabs.wl4.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    
    // Для теста - временное хранилище в памяти
    private List<Task> tasks = new ArrayList<>(Arrays.asList(
        new Task(1L, "Мигрировать с JSF на Spring Boot", "Перенести бизнес-логику", false),
        new Task(2L, "Настроить REST API", "Создать эндпоинты для фронтенда", false),
        new Task(3L, "Подключить PostgreSQL", "Настроить БД для Spring Data JPA", true)
    ));
    
    // Проверка работы API
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "OK",
            "timestamp", LocalDateTime.now().toString(),
            "message", "REST API работает успешно"
        );
    }
    
    // Эмуляция старого JAX-RS эндпоинта
    @GetMapping("/legacy/tasks")
    public List<Task> getLegacyTasks() {
        return tasks;
    }
    
    // CRUD операции (пока без БД)
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return tasks;
    }
    
    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
    
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        task.setId((long) (tasks.size() + 1));
        tasks.add(task);
        return task;
    }
    
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updated) {
        Task task = getTask(id);
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setCompleted(updated.isCompleted());
        return task;
    }
    
    @DeleteMapping("/tasks/{id}")
    public Map<String, Boolean> deleteTask(@PathVariable Long id) {
        tasks.removeIf(t -> t.getId().equals(id));
        return Map.of("deleted", true);
    }
    
    // DTO класс
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Task {
        private Long id;
        private String title;
        private String description;
        private boolean completed;
    }
}