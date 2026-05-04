package com.pm.usermanagement.mapper;

import com.pm.usermanagement.dto.requestDTO;
import com.pm.usermanagement.model.user;
import com.pm.usermanagement.dto.responseDTO;

public class mapperToResponseDTO {
    public static responseDTO mapToResponseDTO(user user) {
        responseDTO dto = new responseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }
    public static user toModel (requestDTO dto) {
        user user = new user();
        return user;
    }
}