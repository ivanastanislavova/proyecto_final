package com.uv.projectApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uv.projectApp.model.Parking;
import com.uv.projectApp.repository.ParkingRepository;


@Service
@Transactional
public class ParkingService {
    @Autowired
    ParkingRepository parkingRepository;

    public List<Parking> findParkings(){
        return parkingRepository.findAll();
    }

    public Optional<Parking> findParkingById(int parkingId){
        return parkingRepository.findById(parkingId);
    }

    public Parking createParking(Parking parking){
        return parkingRepository.save(parking);
    }

    public void deleteParkingById(int parkingId){
        parkingRepository.deleteById(parkingId);
    }
}
