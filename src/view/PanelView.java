package view;

import javax.swing.*;

public class PanelView extends JFrame {

    private static JTextField[] fioTextField;
    protected JLabel[] fioLabel;
    private static JTextField accountNumberTextField;
    protected JLabel accountNumberLabel;
    private static JTextField[] telephoneTextField;
    protected JLabel[] telephoneLabel;
    private static JTextField[] placeResidenceTextField;
    protected JLabel[] placeResidenceLabel;
    protected JCheckBox[] buttonSearch;


    protected void initComponents() {
        fioTextField = new JTextField[3];
        fioLabel = new JLabel[]{new JLabel("Name"), new JLabel("Surname"), new JLabel("Patronymic")};
        accountNumberTextField = new JTextField("", 3);
        accountNumberLabel = new JLabel("AccountNumber");
        telephoneTextField = new JTextField[2];
        telephoneLabel = new JLabel[]{new JLabel("mobileTelephone"), new JLabel("cityTelephone")};
        placeResidenceTextField = new JTextField[5];
        placeResidenceLabel = new JLabel[]{new JLabel("Country"), new JLabel("City"),
                new JLabel("Street"), new JLabel("houseNumber"), new JLabel("flatNumber")};
        buttonSearch = new JCheckBox[]{new JCheckBox("searchFioOrTelephone"), new JCheckBox("searchAccountOrPlace"), new JCheckBox("searchFioAndNumbersOfTelephone")};

    }


    protected void initTextFields() {
        for (int i = 0; i < fioTextField.length; i++) {
            fioTextField[i] = new JTextField("", 3);
        }
        for (int i = 0; i < telephoneTextField.length; i++) {
            telephoneTextField[i] = new JTextField("", 3);
        }
        for (int i = 0; i < placeResidenceTextField.length; i++) {
            placeResidenceTextField[i] = new JTextField("", 3);
        }
    }

    protected void addFioOnPanel(JPanel Panel) {
        for (int i = 0; i < fioLabel.length; i++) {
            Panel.add(fioLabel[i]);
            Panel.add(fioTextField[i]);
        }
    }

    protected void addTelephoneOnPanel(JPanel Panel) {
        for (int i = 0; i < telephoneLabel.length; i++) {
            Panel.add(telephoneLabel[i]);
            Panel.add(telephoneTextField[i]);
        }
    }

    protected void addPlaceOnPanel(JPanel Panel) {
        for (int i = 0; i < placeResidenceLabel.length; i++) {
            Panel.add(placeResidenceLabel[i]);
            Panel.add(placeResidenceTextField[i]);
        }
    }

    protected void addAccountOnPanel(JPanel Panel) {
        Panel.add(accountNumberLabel);
        Panel.add(accountNumberTextField);
    }

    public static JTextField[] getFioTextField() {
        return fioTextField;
    }

    public static JTextField getAccountNumberTextField() {
        return accountNumberTextField;
    }

    public static JTextField[] getTelephoneTextField() {
        return telephoneTextField;
    }

    public static JTextField[] getPlaceResidenceTextField() {
        return placeResidenceTextField;
    }
}
