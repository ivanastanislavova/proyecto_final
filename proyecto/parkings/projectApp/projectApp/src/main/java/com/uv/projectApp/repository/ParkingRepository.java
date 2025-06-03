package com.uv.projectApp.repository;

import com.uv.projectApp.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {

}
