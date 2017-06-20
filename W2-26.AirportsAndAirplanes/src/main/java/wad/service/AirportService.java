package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.Aircraft;
import wad.domain.Airport;
import wad.repository.AircraftRepository;
import wad.repository.AirportRepository;

@Service
public class AirportService {

    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private AirportRepository airportRepository;

    @Transactional
    public void assignAirport(Long aircratId, Long airportId) {
        Aircraft aircraft = aircraftRepository.findOne(aircratId);
        Airport airport = airportRepository.findOne(airportId);

        aircraft.setAirport(airport);
        airport.addAircraft(aircraft);
    }
}
