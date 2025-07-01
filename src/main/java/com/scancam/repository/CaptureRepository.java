package com.scancam.repository;

import com.scancam.model.CaptureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaptureRepository extends JpaRepository<CaptureModel, Long> {
    List<CaptureModel> findByCity(String city);
}
