package com.gtfsapp.api.config.gtfs;

import com.gtfsapp.api.model.entity.Route;
import com.gtfsapp.api.model.entity.Stop;
import com.gtfsapp.api.model.entity.Trip;
import com.gtfsapp.api.repository.RouteRepository;
import com.gtfsapp.api.repository.StopRepository;
import com.gtfsapp.api.repository.TripRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@RequiredArgsConstructor
public class GtfsStaticDataLoader implements ApplicationRunner {

    private final StopRepository stopRepository;
    private final RouteRepository routeRepository;
    private final TripRepository tripRepository;

    @Value("${gtfs.static.data.fetch.url}")
    private String staticDataUrl;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (InputStream inputStream = new URL(staticDataUrl).openStream();
             ZipInputStream zipInputStream = new ZipInputStream(inputStream, StandardCharsets.UTF_8)) {

            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                byte[] fileBytes = zipInputStream.readAllBytes();
                ByteArrayInputStream inMemoryStream = new ByteArrayInputStream(fileBytes);

                switch (entry.getName()) {
                    case "stops.txt" -> loadStops(inMemoryStream);
                    case "routes.txt" -> loadRoutes(inMemoryStream);
                    case "trips.txt" -> loadTrips(inMemoryStream);
                }
            }
        }
    }

    private void loadStops(InputStream inputStream) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<Stop> stops = reader.readAll().stream()
                    .skip(1)
                    .map(param -> new Stop(
                            getOrNull(param, 0),
                            getOrNull(param, 2),
                            parseDoubleSafe(param, 4),
                            parseDoubleSafe(param, 5)
                    ))
                    .collect(Collectors.toList());
            stopRepository.saveAll(stops);
        }
    }

    private void loadRoutes(InputStream inputStream) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<Route> routes = reader.readAll().stream()
                    .skip(1)
                    .map(param -> new Route(
                            getOrNull(param, 0),
                            getOrNull(param, 2),
                            getOrNull(param, 3),
                            getOrNull(param, 5)
                    ))
                    .collect(Collectors.toList());
            routeRepository.saveAll(routes);
        }
    }

    private void loadTrips(InputStream inputStream) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<Trip> trips = reader.readAll().stream()
                    .skip(1)
                    .map(param -> new Trip(
                            getOrNull(param, 2),
                            getOrNull(param, 0),
                            getOrNull(param, 5)
                    ))
                    .collect(Collectors.toList());
            tripRepository.saveAll(trips);
        }
    }

    private String getOrNull(String[] arr, int index) {
        return (arr.length > index && !arr[index].isBlank()) ? arr[index] : null;
    }

    private Double parseDoubleSafe(String[] arr, int index) {
        try {
            String value = getOrNull(arr, index);
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
