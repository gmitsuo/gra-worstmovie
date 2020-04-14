package com.gra.worstmovieapp.controllers;

import com.gra.worstmovieapp.services.AwardsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/awards")
public class AwardsController {

    private final AwardsService awardsService;

    public AwardsController(AwardsService awardsService) {
        this.awardsService = awardsService;
    }

    @GetMapping("/min-max-winning-interval")
    public ResponseEntity<?> getMinAndMaxWinningInterval() {
        return ResponseEntity.of(awardsService.getMinAndMaxWinningInterval());
    }
}
