package com.pm.usermanagement.controller;

import com.pm.usermanagement.service.userService;
import jakarta.validation.groups.Default;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.pm.usermanagement.service.userService;
import com.pm.usermanagement.dto.requestDTO;
import com.pm.usermanagement.dto.responseDTO;
import java.util.List;
import java.util.UUID;

import com.pm.usermanagement.dto.validator.createUserValidator;
@RestController
@RequestMapping("/user")

public class userController {
    private final userService userService;

    public userController(userService userService) {
        this.userService = userService;
    }
//
//    @GetMapping
//    public ResponseEntity<List<responseDTO>> getUsers() {
//        List<responseDTO> users = userService.getUsers();
//        return ResponseEntity.ok().body(users);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<responseDTO> getUserById(@PathVariable UUID id) {
        responseDTO user = userService.getUserById(id);
//       return ResponseEntity.ok().body(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<responseDTO>> getUserBySearch(
            @RequestParam (defaultValue = "0") int page ,
            @RequestParam(defaultValue = "10") int size ,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age // use this as the Integer can be null but not int
    ) {
        Page<responseDTO> user = userService.getUserBySearch(page, size, name, age);
        return ResponseEntity.ok(user);
    }


    @GetMapping
    public ResponseEntity<PagedModel<responseDTO>> getPagedUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<responseDTO> users = userService.getPagedUsers(page, size);
        return ResponseEntity.ok().body(new PagedModel<>(users));
    }

    // Page<T> (Interface) -> When you call repository.findAll(pageable), it returns a Page<T>. PLUS metadata (total elements in DB, total pages, is it the last page
    // Slice<T> (Interface) -> lightweight" version of Page.Slice knows if there is a next page, but it does not count the total number of records in the database. perfect in the case there are millions of pages as cnt(*) is very slow in this case so it is infinite scroll case
    // PagedModel<T> -> Spring HATEOAS. It wraps your Page<T> and adds "Links." It follows the HAL (Hypertext Application Language) standard.It will include a _links section with URLs for self, next, and prev. asIn a standard API, the frontend has to "guess" the next URL (e.g., currentPage + 1). In a HAL-compliant API using PagedModel, the server tells the client exactly where to go next by providing URLs (links).
    // PagingResult<T> -> It is a Custom DTO that developers write themselves. as there are many things which are of no use so we use this to make a custom as per our requirement

    @PostMapping
    public ResponseEntity<responseDTO> createUser(@Validated({Default.class,createUserValidator.class}) @RequestBody requestDTO requestDTO) {
        responseDTO createdUser = userService.createUser(requestDTO);
        return ResponseEntity.ok().body(createdUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<responseDTO> update(@PathVariable UUID id , @Validated(Default.class) @RequestBody requestDTO requestDTO){
        responseDTO response = userService.updateUser(id ,requestDTO);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        responseDTO response = userService.deleteUser(id);
    }


}
