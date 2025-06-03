package com.uv.projectApp.repository;

import com.uv.projectApp.model.ParkingEvent;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingEventRepository extends MongoRepository<ParkingEvent, String> {
    List<ParkingEvent> findAll();
    List<ParkingEvent> findByParkingIdOrderByTimestampDesc(Integer parkingId);
    List<ParkingEvent> findByParkingIdAndTimestampBetweenOrderByTimestamp(Integer parkingId, LocalDateTime from, LocalDateTime to);
    List<ParkingEvent> findByTimestamp(LocalDateTime timestamp);
}
