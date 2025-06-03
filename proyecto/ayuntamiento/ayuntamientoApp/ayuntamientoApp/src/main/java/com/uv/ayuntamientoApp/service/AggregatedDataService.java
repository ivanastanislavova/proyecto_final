package com.uv.ayuntamientoApp.service;

import com.uv.ayuntamientoApp.dto.EstacionDTO;
import com.uv.ayuntamientoApp.dto.ParkingDTO;
import com.uv.ayuntamientoApp.dto.AirQualityDTO;
import com.uv.ayuntamientoApp.dto.AggregatedDataSnapshotDTO;
import com.uv.ayuntamientoApp.service.ParkingClientService;
import com.uv.ayuntamientoApp.service.EstacionClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AggregatedDataService {

    @Autowired
    private ParkingClientService aparcamientoClientService;

    @Autowired
    private EstacionClientService estacionClientService;

    public Mono<AggregatedDataSnapshotDTO> obtenerDatosAgregados() {
        Flux<ParkingDTO> parkingsFlux = aparcamientoClientService.obtenerParkings();
        Flux<EstacionDTO> estacionesFlux = estacionClientService.obtenerTodos();

        return parkingsFlux.collectList()
            .zipWith(estacionesFlux.collectList())
            .map(tuple -> {
                List<ParkingDTO> parkings = tuple.getT1();
                List<EstacionDTO> estaciones = tuple.getT2();

                List<AggregatedDataSnapshotDTO.AparcamientoData> datos = parkings.stream().map(p -> {
                    EstacionDTO estacionCercana = estaciones.stream()
                        .min(Comparator.comparingDouble(e ->
                            distancia(p.getLatitude(), p.getLongitude(), e.getLatitude(), e.getLongitude())))
                        .orElse(null);

                    AirQualityDTO calidadAire = estacionCercana != null
                        ? new AirQualityDTO(10.0, 5.0, 7.5, 12.0) // Valores simulados
                        : new AirQualityDTO(0.0, 0.0, 0.0, 0.0);

                    return new AggregatedDataSnapshotDTO.AparcamientoData(
                        p.getId(),
                        Math.random() * p.getBikesCapacity(), // Simulado
                        calidadAire
                    );
                }).collect(Collectors.toList());

                return new AggregatedDataSnapshotDTO(LocalDateTime.now(), datos);
            });
    }

    
    private double distancia(float lat1, float lon1, float lat2, float lon2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }
}
