package com.uv.estacionApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uv.estacionApp.model.Estacion;

public interface EstacionRepository extends JpaRepository<Estacion, Integer> {

}
