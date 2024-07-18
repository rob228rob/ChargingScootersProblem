package com.example.ScootersRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScootersRepository extends CrudRepository<ScooterEntity, Long> {

    List<ScooterEntity> findAll();

    Optional<ScooterEntity> findById(long id);

    List<ScooterEntity> findAllByChangedRecentlyTrueAndIdNotNullAndAvailableTrue();
}
