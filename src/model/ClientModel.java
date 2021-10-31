package model;

import java.util.ArrayList;
import java.util.List;

public class ClientModel {

    private final static List<ClientModel> persons = new ArrayList<>(100);
    private final FIOModel fio;
    private final String accountNumber;
    private final PlaceModel placeResidence;
    private final TelephoneModel telephone;

    public ClientModel(FIOModel fio, String accountNumber, PlaceModel placeResidence, TelephoneModel telephone) {
        this.fio = fio;
        this.accountNumber = accountNumber;
        this.placeResidence = placeResidence;
        this.telephone = telephone;
    }

    public FIOModel getFio() {
        return fio;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public PlaceModel getPlaceResidence() {
        return placeResidence;
    }

    public TelephoneModel getTelephone() {
        return telephone;
    }

    public static List<ClientModel> getPersons() {
        return persons;
    }
}
