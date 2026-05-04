package com.pm.usermanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class requestDTO {

    @NotBlank(message = "Name cannot be empty")
    public String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    public String email;

    @Min(value = 1, message = "Age must be greater than 0")
    public Integer age;
}