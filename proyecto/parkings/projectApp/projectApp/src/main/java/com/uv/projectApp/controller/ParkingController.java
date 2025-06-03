package com.uv.projectApp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uv.projectApp.model.Parking;
import com.uv.projectApp.services.ParkingService;

@RestController
@RequestMapping("/api")
public class ParkingController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private ParkingService service;

    @GetMapping("/aparcamientos")
    public List<Parking> listar() {
        return service.findParkings();
    }

    @GetMapping("/aparcamiento/{id}")
    public Optional<Parking> findParkingById(@PathVariable("id") Integer id) {
        LOGGER.debug("View Parking with ID: " + id);
        Optional<Parking> parking = service.findParkingById(id);
        return parking;
    }

    @PostMapping("/aparcamiento")
    public ResponseEntity<Parking> createParking(@RequestBody Parking parking){
        LOGGER.debug("Create parking: {}", parking);
        // Validación básica de campos obligatorios
        if (parking.getDirection() == null || parking.getBikesCapacity() == 0) {
            LOGGER.warn("Intento de crear parking con datos inválidos: {}", parking);
            return ResponseEntity.badRequest().build();
        }
        try {
            Parking createdParking = service.createParking(parking);
            LOGGER.info("Parking creado correctamente: {}", createdParking);
            return new ResponseEntity<>(createdParking, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error al crear parking", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("aparcamiento/{id}")
    public ResponseEntity<Void> deleteParkingById(@PathVariable("id") Integer id){
        LOGGER.info("[DELETE] Petición recibida para borrar parking con ID: {}", id);
        service.deleteParkingById(id);
        LOGGER.info("[DELETE] Se ejecutó service.deleteParkingById para ID: {}", id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/aparcamiento/{id}")
    public ResponseEntity<Parking> patchParking(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Optional<Parking> optionalParking = service.findParkingById(id);
        if (optionalParking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Parking existing = optionalParking.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "direction":
                    existing.setDirection((String) value);
                    break;
                case "bikesCapacity":
                    if (value instanceof Number) {
                        existing.setBikesCapacity(((Number) value).intValue());
                    }
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

        Parking updated = service.createParking(existing); // o updateParking() si lo tienes separado
        return ResponseEntity.ok(updated);
    }



   /*  @PostMapping
    public Parking guardar(@RequestBody Parking parking) {
        return repository.save(parking);
    }*/
}
