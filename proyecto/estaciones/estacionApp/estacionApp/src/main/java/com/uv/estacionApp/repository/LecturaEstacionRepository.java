package com.uv.estacionApp.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.uv.estacionApp.model.LecturaEstacion;

public interface LecturaEstacionRepository extends MongoRepository<LecturaEstacion, String> {
    List<LecturaEstacion> findAll();
    List<LecturaEstacion> findByEstacionIdOrderByTimestampDesc(Integer estacionId);
    List<LecturaEstacion> findByEstacionIdAndTimestampBetweenOrderByTimestamp(Integer estacionId, LocalDateTime from, LocalDateTime to);
    List<LecturaEstacion> findByTimestamp(LocalDateTime timestamp);
}
