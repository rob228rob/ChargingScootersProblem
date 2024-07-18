package com.example.ScooterService;

import lombok.RequiredArgsConstructor;
import com.example.ScooterDTO.ScooterDTO;
import com.example.ScootersRepository.ScooterEntity;
import com.example.ScootersRepository.ScootersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScooterService {

    private final ScootersRepository scootersRepository;

    private final ModelMapper mapper;

    public List<ScooterDTO> getAllScooters() {
        List<ScooterEntity> allScooters = scootersRepository.findAll();
        List<ScooterDTO> allScootersDTO = allScooters.stream()
                .map(x -> {
                    return mapper.map(x, ScooterDTO.class);
                })
                .collect(Collectors.toList());

        return allScootersDTO;
    }

    public void addScooter(ScooterDTO scooterDTO) {
        ScooterEntity scooterEntity = new ScooterEntity();
        mapper.map(scooterDTO, scooterEntity);
        scootersRepository.save(scooterEntity);
    }

    public void updateScooter(ScooterDTO scooterDTO) {
        ScooterEntity scooterEntity = new ScooterEntity();
        mapper.map(scooterDTO, scooterEntity);
        scootersRepository.save(scooterEntity);
    }

    public void deleteScooter(ScooterDTO scooterDTO) {
        ScooterEntity scooterEntity = new ScooterEntity();
        mapper.map(scooterDTO, scooterEntity);
        scootersRepository.delete(scooterEntity);
    }

    public ScooterDTO getScooterById(long id) {
        Optional<ScooterEntity> scooterEntity = scootersRepository.findById(id);

        if (scooterEntity.isPresent()) {
            return mapper.map(scooterEntity.get(), ScooterDTO.class);
        }

        return null;
    }

    public long getRecordsCount() {
        return scootersRepository.count();
    }

    public List<ScooterDTO> getScootersByRecentlyChange() {
        return scootersRepository.findAllByChangedRecentlyTrueAndIdNotNullAndAvailableTrue()
                .stream()
                .map(x -> mapper.map(x, ScooterDTO.class))
                .toList();
    }
}
