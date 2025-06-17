package com.scancam.repository;

import com.scancam.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long> {
    Optional<CityModel> findByName(String name);
}
