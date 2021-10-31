package model;

import javax.swing.*;

public class PlaceModel {
    private final String country;
    private final String city;
    private final String street;
    private Integer houseNumber;
    private Integer flatNumber;

    public PlaceModel(JTextField[] placeResidence) {
        this.city = placeResidence[1].getText();
        this.country = placeResidence[0].getText();
        this.street = placeResidence[2].getText();
        if(placeResidence[3].getText().equals("") && placeResidence[4].getText().equals("")){
            this.houseNumber = 0;
            this.flatNumber = 0;
        }
        else {
            if (placeResidence[3].getText().equals("")) {
                this.houseNumber = 0;
                this.flatNumber = Integer.parseInt(placeResidence[4].getText());
            }
            if (placeResidence[4].getText().equals("")) {
                this.flatNumber = 0;
                this.houseNumber = Integer.parseInt(placeResidence[3].getText());
            } else {
                this.houseNumber = Integer.parseInt(placeResidence[3].getText());
                this.flatNumber = Integer.parseInt(placeResidence[4].getText());
            }
        }
    }

    public PlaceModel(String country, String city, String street, String houseNumber, String flatNumber)
    {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = Integer.parseInt(houseNumber.trim());
        this.flatNumber = Integer.parseInt(flatNumber.trim());
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getFlatNumber() {
        return flatNumber;
    }
}
