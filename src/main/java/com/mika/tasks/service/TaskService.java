package com.mika.tasks.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mika.tasks.entity.User;
import com.mika.tasks.dto.TaskRequestDTO;
import com.mika.tasks.dto.TaskResponseDTO;
import com.mika.tasks.dto.TaskUpdateDTO;
import com.mika.tasks.entity.Task;
import com.mika.tasks.entity.TaskStatus;
import com.mika.tasks.repository.TaskRepository;
import com.mika.tasks.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    
    public TaskRequestDTO createTask(TaskRequestDTO taskDTO){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> result = userRepository.findByEmail(email);
        User user = result.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Task t = new Task();
        t.setTitle(taskDTO.title());
        t.setDescription(taskDTO.description());
        t.setUser(user);
        t.setTaskStatus(TaskStatus.PENDING);
        taskRepository.save(t);
        TaskRequestDTO taskDTO2 = new TaskRequestDTO(taskDTO.title(), taskDTO.description());
        return taskDTO2;   
    }
    public List<TaskResponseDTO> getTasksByUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> result = userRepository.findByEmail(email);
        User user = result.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Task> tasks = taskRepository.findByUser(user);
        return tasks.stream()
            .map(t -> new TaskResponseDTO(t.getId(), t.getTitle(), t.getDescription(), t.getTaskStatus(), t.getDate(), t.getUser().getName()))
            .collect(Collectors.toList());
        /*Seria assim sem o stream: List<Task> tasks = taskRepository.findByUser(user);
        List<TaskResponseDTO> result2 = new ArrayList<>();
        for(Task t : tasks) {
            result2.add(new TaskResponseDTO(t.getId(), t.getTitle(), t.getDescription(), t.getTaskStatus(), t.getDate(), t.getUser().getName()));
            return result2;*/
    }
    public TaskResponseDTO updateTaskById(Long id, TaskUpdateDTO taskUpdateDTO){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Task> result = taskRepository.findById(id);
        if(result.get().getUser().getEmail().equals(email)){
            Task task = result.get();
            if(taskUpdateDTO.title() != null)task.setTitle(taskUpdateDTO.title());
            if(taskUpdateDTO.description() != null)task.setDescription(taskUpdateDTO.description());
            if(taskUpdateDTO.status() != null)task.setTaskStatus(taskUpdateDTO.status());
            taskRepository.save(task);
            TaskResponseDTO taskResponse = new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getTaskStatus(), task.getDate(), task.getUser().getName());
            return taskResponse;
        }else{
            throw new RuntimeException("Você não tem permissão para editar essa task!");
        }
        }
    public String deleteTaskById(Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Task> result = taskRepository.findById(id);
        if(result.get().getUser().getEmail().equals(email)){
            Task taskDelete = result.get();
            taskRepository.delete(taskDelete);
            return "Task deletada com sucesso.";
        }else   throw new RuntimeException("Você não tem permissão para editar essa task!");
        }
    }


    


