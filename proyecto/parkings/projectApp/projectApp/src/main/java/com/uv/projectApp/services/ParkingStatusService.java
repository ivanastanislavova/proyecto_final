package com.uv.projectApp.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uv.projectApp.model.ParkingEvent;
import com.uv.projectApp.repository.ParkingEventRepository;

@Service
public class ParkingStatusService {

    @Autowired
    private ParkingEventRepository repository;

    public ParkingEvent registerEvent(ParkingEvent event) {
        event.setTimestamp(LocalDateTime.now());

        ParkingEvent last = getLatestStatus(event.getParkingId());

        if (last != null) {
            int bikes = last.getBikesAvailable();
            int spots = last.getFreeParkingSpots();

            switch (event.getOperation()) {
                case "rent":
                    if (bikes == 0) {
                        throw new IllegalStateException("No hay bicicletas disponibles para alquilar.");
                    }
                    bikes--;
                    spots++;
                    break;

                case "return":
                    if (spots == 0) {
                        throw new IllegalStateException("No hay huecos disponibles para aparcar la bicicleta.");
                    }
                    bikes++;
                    spots--;
                    break;

                default:
                    throw new IllegalArgumentException("Operación no válida: " + event.getOperation());
            }

            event.setBikesAvailable(bikes);
            event.setFreeParkingSpots(spots);
        } else {
            // Primer evento para ese aparcamiento: inicializar valores
            if (event.getBikesAvailable() < 0 || event.getFreeParkingSpots() < 0) {
                throw new IllegalArgumentException("Valores iniciales inválidos para bicicletas o huecos.");
            }
        }

        return repository.save(event);
    }


    public ParkingEvent getLatestStatus(Integer parkingId) {
        return repository.findByParkingIdOrderByTimestampDesc(parkingId)
                         .stream().findFirst().orElse(null);
    }

    public List<ParkingEvent> getStatusBetween(Integer parkingId, LocalDateTime from, LocalDateTime to) {
        return repository.findByParkingIdAndTimestampBetweenOrderByTimestamp(parkingId, from, to);
    }

    public List<ParkingEvent> getTop10LatestByParking() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                    ParkingEvent::getParkingId,
                    Collectors.maxBy(Comparator.comparing(ParkingEvent::getTimestamp))
                ))
                .values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparingInt(ParkingEvent::getBikesAvailable).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

}
