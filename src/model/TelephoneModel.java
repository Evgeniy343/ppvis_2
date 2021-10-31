package model;

import javax.swing.*;

public class TelephoneModel {
    private int mobileTelephone;
    private int cityTelephone;

    public TelephoneModel(JTextField[] telephone) {
        if(telephone[0].getText().equals("") && telephone[1].getText().equals("")){
            this.mobileTelephone = 0;
            this.cityTelephone = 0;
        }
        else {
            if (telephone[0].getText().equals("")) {
                this.mobileTelephone = 0;
                this.cityTelephone = Integer.parseInt(telephone[1].getText());
            }
            if (telephone[1].getText().equals("")) {
                this.mobileTelephone = Integer.parseInt(telephone[0].getText());
                this.cityTelephone = 0;
            } else {
                this.mobileTelephone = Integer.parseInt(telephone[0].getText());
                this.cityTelephone = Integer.parseInt(telephone[1].getText());
            }
        }
    }

    public TelephoneModel(String mobileTelephone, String cityTelephone)
    {
        this.mobileTelephone = Integer.parseInt(mobileTelephone.trim());
        this.cityTelephone = Integer.parseInt(cityTelephone.trim());
    }

    public int getMobileTelephone() {
        return mobileTelephone;
    }

    public int getCityTelephone() {
        return cityTelephone;
    }
}
