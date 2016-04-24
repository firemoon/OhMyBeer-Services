package fr.nicolasgodefroy.ohmybeer.model;

import lombok.Data;

/**
 * Created by nicolasgodefroy on 10/04/2016.
 */
@Data
public class GeoLocation {
    private String accuracy;
    private double lat;
    private double lon;
}
