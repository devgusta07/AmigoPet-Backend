package com.petshop.petshop_api.controller;

import com.petshop.petshop_api.dto.request.tutor.TutorRequestDTO;
import com.petshop.petshop_api.dto.response.tutor.TutorResponseDTO;
import com.petshop.petshop_api.service.CreateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creat")
public class CreateController {

    private final CreateService creatService;

    public CreateController(CreateService creatService) {
        this.creatService = creatService;
    }

    @PostMapping
    public ResponseEntity<TutorResponseDTO> creatUser(
           @Valid @RequestBody TutorRequestDTO tutorRequestDTO
    ){

        return ResponseEntity.status(HttpStatus.CREATED).body(creatService.createTutor(tutorRequestDTO));
    }
}
