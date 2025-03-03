package sg.edu.nus.iss.csf.workshop37.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.csf.workshop37.server.models.City;
import sg.edu.nus.iss.csf.workshop37.server.repositories.CitiesRepository;

@Service
public class CitiesService {
    @Autowired
    private CitiesRepository citiesRepo;

    public Optional<List<City>> getAllCities() {
        List<City> cc=  this.citiesRepo.getAllCities();
        if(cc != null)
            return Optional.of(cc);
        return Optional.empty();
    };
}
