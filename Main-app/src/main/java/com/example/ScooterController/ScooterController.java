package com.example.ScooterController;

import com.example.ScooterDTO.ScooterDTO;
import lombok.RequiredArgsConstructor;
import com.example.ScooterModel.ScooterModel;
import com.example.ScooterService.ScooterService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scooters")
@RequiredArgsConstructor
public class ScooterController {

    private final ScooterService scooterService;

    @GetMapping
    @RequestMapping("/get-all")
    public ResponseEntity<List<ScooterDTO>> getScooters() {
        List<ScooterDTO> allScooters = scooterService.getAllScooters();

        return ResponseEntity.ok(allScooters);
    }

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<ScooterDTO> addScooter(@RequestBody ScooterDTO scooterDTO) {
        scooterService.addScooter(scooterDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
