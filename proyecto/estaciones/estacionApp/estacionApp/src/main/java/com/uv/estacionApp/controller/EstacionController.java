package com.uv.estacionApp.controller;

import com.uv.estacionApp.model.Estacion;
import com.uv.estacionApp.service.EstacionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estacion")
public class EstacionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(EstacionController.class);

    @Autowired
    private EstacionService service;

    @GetMapping("/estaciones")
    public List<Estacion> listar() {
        return service.findEstaciones();
    }

    @GetMapping("/estacion/{id}")
    public Optional<Estacion> findParkingById(@PathVariable("id") Integer id) {
        LOGGER.debug("View Estacion with ID: " + id);
        Optional<Estacion> estacion = service.findEstacionById(id);
        return estacion;
    }

    @PostMapping("/estacion")
    public ResponseEntity<Estacion> createEstacion(@RequestBody Estacion estacion){
        LOGGER.debug("Create estacion");
        Estacion createdEstacion = service.createEstacion(estacion);
        return new ResponseEntity<>(createdEstacion, HttpStatus.CREATED);
    }

    @DeleteMapping("/estacion/{id}")
    public ResponseEntity<Void> deleteEstacionById(@PathVariable("id") Integer id){
        LOGGER.debug("Delete estacion with ID: " + id);
        service.deleteEstacionById(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/estacion/{id}")
    public ResponseEntity<Estacion> patchEstacion(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Optional<Estacion> optionalEstacion = service.findEstacionById(id);
        if (optionalEstacion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Estacion existing = optionalEstacion.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "direction":
                    existing.setDirection((String) value);
                    break;
                case "latitude":
                    if (value instanceof Number) {
                        existing.setLatitude(((Number) value).floatValue());
                    }
                    break;
                case "longitude":
                    if (value instanceof Number) {
                        existing.setLongitude(((Number) value).floatValue());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Campo no permitido: " + key);
            }
        });

        Estacion updated = service.createEstacion(existing); // o updateParking() si lo tienes separado
        return ResponseEntity.ok(updated);
    }



   /*  @PostMapping
    public Parking guardar(@RequestBody Parking parking) {
        return repository.save(parking);
    }*/
}
