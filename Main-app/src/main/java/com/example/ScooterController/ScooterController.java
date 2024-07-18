package com.example.ScooterController;

import com.example.ScooterDTO.ScooterDTO;
import lombok.RequiredArgsConstructor;
import com.example.ScooterService.ScooterService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/scooters")
@RequiredArgsConstructor
public class ScooterController {

    private final ScooterService scooterService;

    @GetMapping
    @RequestMapping("/get-all")
    public ResponseEntity<List<ScooterDTO>> getScooters() {
        List<ScooterDTO> allScooters = scooterService.getAllScooters();

        return ResponseEntity.ok(allScooters);
    }

    @GetMapping
    @RequestMapping("/get/{id}")
    public ResponseEntity<ScooterDTO> getScooter(@PathVariable long id) {
        ScooterDTO scooterById = scooterService.getScooterById(id);
        if (scooterById == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(scooterById);
    }

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<ScooterDTO> addScooter(@RequestBody ScooterDTO scooterDTO) {
        scooterService.addScooter(scooterDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @RequestMapping("/update")
    public ResponseEntity<ScooterDTO> updateScooter(@RequestBody ScooterDTO scooterDTO) {
        scooterService.updateScooter(scooterDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public ResponseEntity<ScooterDTO> deleteScooter(@RequestBody ScooterDTO scooterDTO) {
        scooterService.deleteScooter(scooterDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
