package model;

import javax.swing.*;

public class FIOModel {
    private final String name;
    private final String surname;
    private final String patronymic;

    public FIOModel(JTextField[] fio) {
        this.name = fio[0].getText();
        this.surname = fio[1].getText();
        this.patronymic = fio[2].getText();
    }

    public FIOModel(String name, String surname, String patronymic)
    {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public String getName() { return name; }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
