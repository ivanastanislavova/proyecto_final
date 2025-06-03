package com.uv.estacionApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uv.estacionApp.model.Estacion;
import com.uv.estacionApp.repository.EstacionRepository;


@Service
@Transactional
public class EstacionService{
    @Autowired
    EstacionRepository estacionRepository;

    public List<Estacion> findEstaciones(){
        return estacionRepository.findAll();
    }

    public Optional<Estacion> findEstacionById(int estacionId){
        return estacionRepository.findById(estacionId);
    }

    public Estacion createEstacion(Estacion estacion){
        return estacionRepository.save(estacion);
    }

    public void deleteEstacionById(int estacionId){
        estacionRepository.deleteById(estacionId);
    }
}
