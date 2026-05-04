package com.pm.usermanagement.service;

import com.pm.usermanagement.dto.requestDTO;
import com.pm.usermanagement.dto.responseDTO;
import com.pm.usermanagement.model.user;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.pm.usermanagement.Repository.Repository;

import java.util.Optional;
import java.util.UUID;
import com.pm.usermanagement.mapper.mapperToResponseDTO;
import com.pm.usermanagement.Exception.ResourceNotFoundException;
import com.pm.usermanagement.Exception.EmailAlreadyExist;
import com.pm.usermanagement.Exception.UserNotFound;
import static com.pm.usermanagement.mapper.mapperToResponseDTO.toModel;

@Service
public class userService {

    private final Repository userRepository;
    public userService(Repository repository) {
        this.userRepository = repository;
    }

    public responseDTO getUserById(UUID id) {
        return userRepository.findById(id)
//                .map(user -> mapToResponseDTO(user))
                .map(mapperToResponseDTO::mapToResponseDTO) //  Works if method is static
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // FIXED: Now actually calls the repository to search
    public Page<responseDTO> getUserBySearch(int page, int size, String name, Integer age) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        // Assuming your repository has this method:
        return userRepository.findByNameContainingAndAge(name, age, pageable)
                .map(mapperToResponseDTO::mapToResponseDTO);
    }

    public Page<responseDTO> getPagedUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(mapperToResponseDTO::mapToResponseDTO);
    }

    public responseDTO createUser(requestDTO requestDTO) {
//        if (userRepository.findByEmail(requestDTO.email) == null) { // it does not written boolean
        if (userRepository.findByEmail(requestDTO.getEmail()).isEmpty()) {
            user newUser = userRepository.save(mapperToResponseDTO.toModel(requestDTO));
            return mapperToResponseDTO.mapToResponseDTO(newUser);
        } else {
            throw new EmailAlreadyExist("Email Already exist");
        }
    }

    public responseDTO updateUser(UUID id, requestDTO requestDTO) {
        user existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("User not found"));
        if (requestDTO.email != null) {
//            If email exists (even if it belongs to the SAME user) → ❌ throw exception
//            if (userRepository.findByEmail(requestDTO.email) == null) {
//            user userWithEmail = userRepository.findByEmail(requestDTO.email);
            Optional<user> userWithEmail = userRepository.findByEmail(requestDTO.email); // findby email return optional so that why we have change
            // Reject ONLY if email belongs to another user
            if (userWithEmail.isPresent() && !userWithEmail.get().id.equals(id)) {
                throw new EmailAlreadyExist("Email already exists");
            }
            existingUser.email = requestDTO.email;
        }
        if (requestDTO.name != null) {existingUser.name = requestDTO.name;}
        return mapperToResponseDTO.mapToResponseDTO(userRepository.save(existingUser));
    }

    public responseDTO deleteUser(UUID id) {
        if (userRepository.findById(id).isPresent()) {userRepository.deleteById(id);}
        return null;
    }
}
