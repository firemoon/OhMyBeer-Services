package fr.nicolasgodefroy.ohmybeer.model;

import lombok.Data;

import java.util.List;

/**
 * Created by nicolasgodefroy on 10/04/2016.
 */
@Data
public class Brewery {
    private String name;
    private String city;
    private String state;
    private String code;
    private String country;
    private String phone;
    private String website;
    private String type;
    private String updated;
    private String description;
    private List<String> address;
    private GeoLocation geo;
}
