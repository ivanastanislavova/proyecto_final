package com.uv.estacionApp.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.uv.estacionApp.model.LecturaEstacion;
import com.uv.estacionApp.repository.LecturaEstacionRepository;

@Service
public class LecturaEstacionService {

    @Autowired
    private LecturaEstacionRepository repository;

    public LecturaEstacion registerEvent(LecturaEstacion event) {
        event.setTimestamp(LocalDateTime.now());
        return repository.save(event);
    }


    public LecturaEstacion getLatestStatus(Integer estacionId) {
        return repository.findByEstacionIdOrderByTimestampDesc(estacionId)
                         .stream().findFirst().orElse(null);
    }

    public List<LecturaEstacion> getStatusBetween(Integer parkingId, LocalDateTime from, LocalDateTime to) {
        return repository.findByEstacionIdAndTimestampBetweenOrderByTimestamp(parkingId, from, to);
    }



}
