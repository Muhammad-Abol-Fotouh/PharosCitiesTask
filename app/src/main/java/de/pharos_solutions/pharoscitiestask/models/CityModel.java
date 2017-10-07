package de.pharos_solutions.pharoscitiestask.models;

import java.util.Comparator;

/**
 * Created by muhammadkorany on 10/5/17.
 */
public class CityModel implements Comparator<CityModel>{

    private String country;
    private String name;
    private String _id;
    private CoordModel coord;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public CoordModel getCoord() {
        return coord;
    }

    public void setCoord(CoordModel coord) {
        this.coord = coord;
    }

    @Override
    public int compare(CityModel o1, CityModel o2) {
        int nameComparison = o1.name.compareTo(o2.name);
        return nameComparison == 0 ? o1.country.compareTo(o2.country) : nameComparison;
    }
}
