package sg.edu.nus.iss.csf.workshop37.server.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class City {
    private String code;
    private String cityName;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

     public static City populate(ResultSet rs) throws SQLException {
        City c = new City();
        c.setCode(rs.getString("code"));
        c.setCityName(rs.getString("city_name"));
        return c;
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                    .add("code", getCode())
                    .add("city_name", getCityName())
                    .build();
    }
}
