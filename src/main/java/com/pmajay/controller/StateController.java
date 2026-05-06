package com.pmajay.controller;

import com.pmajay.model.State;
import com.pmajay.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    public ResponseEntity<List<State>> getAllStates() {
        return ResponseEntity.ok(stateRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<State> createState(@RequestBody State state) {
        return ResponseEntity.ok(stateRepository.save(state));
    }
}
