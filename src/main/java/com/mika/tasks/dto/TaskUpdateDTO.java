package com.mika.tasks.dto;

import com.mika.tasks.entity.TaskStatus;

public record TaskUpdateDTO(String title, String description, TaskStatus status) {
    
}
