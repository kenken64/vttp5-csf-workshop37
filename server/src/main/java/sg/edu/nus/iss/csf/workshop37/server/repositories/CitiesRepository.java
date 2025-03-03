package sg.edu.nus.iss.csf.workshop37.server.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.csf.workshop37.server.models.City;

@Repository
public class CitiesRepository {
    private static final String SELECT_ALL_CITIES 
        = "SELECT code, city_name FROM cities";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<City> getAllCities() {
    return jdbcTemplate.query(SELECT_ALL_CITIES, (rs, rowNum) -> {
        return City.populate(rs);
    });
}
}
