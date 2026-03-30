package com.mika.tasks.dto;

import java.util.Date;

import com.mika.tasks.entity.TaskStatus;

public record TaskResponseDTO(Long id, String title, String description, TaskStatus status, Date date, String username) {} 