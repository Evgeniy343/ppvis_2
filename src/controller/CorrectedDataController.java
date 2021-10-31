package controller;

import view.PanelCreateView;

import javax.swing.*;
import java.nio.charset.StandardCharsets;

public class CorrectedDataController {
    public boolean isCorrectedData(JTextField[] telephone, JTextField[] place) {
        boolean isCorrected = true;
        isCorrected = isCorrectedTelephone(telephone, isCorrected);
        isCorrected = isCorrectedPlace(place, isCorrected);
        return isCorrected;
    }


    public boolean isCorrectedTelephone(JTextField[] telephone, boolean isCorrected) {
        for (int i = 0; i < telephone[0].getText().length(); i++) {
            String tel = telephone[0].getText();
            isCorrected = compareASCII(tel, isCorrected, i);
            if (!isCorrected) {
                return isCorrected;
            }
        }
        for (int i = 0; i < telephone[1].getText().length(); i++) {
            String tel = telephone[1].getText();
            isCorrected = compareASCII(tel, isCorrected, i);
            if (!isCorrected) {
                return isCorrected;
            }
        }
        return isCorrected;
    }

    public boolean isCorrectedPlace(JTextField[] place, boolean isCorrected) {
        for (int i = 0; i < place[3].getText().length(); i++) {
            String tel = place[3].getText();
            isCorrected = compareASCII(tel, isCorrected, i);
            if (!isCorrected) {
                return isCorrected;
            }
        }
        for (int i = 0; i < place[4].getText().length(); i++) {
            String tel = place[4].getText();
            isCorrected = compareASCII(tel, isCorrected, i);
            if (!isCorrected) {
                return isCorrected;
            }
        }
        return isCorrected;
    }

    public boolean isCorrectedPerPage(JTextField perPage) {
        String perPageUpdate = perPage.getText();
        for (int i = 0; i < perPageUpdate.length(); i++) {
            if (!compareASCII(perPageUpdate, true, i) || perPageUpdate.equals("")) {
                return false;
            }
        }
        return true;
    }

    private boolean compareASCII(String tel, boolean isCorrected, int i) {
        byte[] bytes = tel.getBytes(StandardCharsets.US_ASCII);
        if (bytes[i] > 57 || bytes[i] < 48) {
            isCorrected = false;
        }
        return isCorrected;
    }

    public static boolean isFIOEmpty() {
        int counterParamEmpty = 0;
        for (int i = 0; i < PanelCreateView.getFioTextField().length; i++) {
            if (PanelCreateView.getFioTextField()[i].getText().equals("")) {
                counterParamEmpty++;
            }
        }
        if (counterParamEmpty > 0) {
            return true;
        }
        return false;
    }

    public static boolean isAccountEmpty() {
        if (PanelCreateView.getAccountNumberTextField().getText().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isAddressEmpty() {
        int counterParamEmpty = 0;
        for (int i = 0; i < PanelCreateView.getPlaceResidenceTextField().length; i++) {
            if (PanelCreateView.getPlaceResidenceTextField()[i].getText().equals("")) {
                counterParamEmpty++;
            }
        }
        if (counterParamEmpty > 0) {
            return true;
        }
        return false;
    }

    public static boolean isTelephoneEmpty() {
        int counterParamEmpty = 0;
        for (int i = 0; i < PanelCreateView.getTelephoneTextField().length; i++) {
            if (PanelCreateView.getTelephoneTextField()[i].getText().equals("")) {
                counterParamEmpty++;
            }
        }
        if (counterParamEmpty > 0) {
            return true;
        }
        return false;
    }
}
