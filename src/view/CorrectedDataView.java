package view;

import javax.swing.*;

public class CorrectedDataView {
    public static void errorEnter() {
        String message = "Incorrect telephone number or number of house or number of flat entered!";
        JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEnterPerPage() {
        String message = "Incorrect perPage entered!";
        JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorFind() {
        String message = "Person don't find!";
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE);
    }

    public static void enterData() {
        String message = "Enter data!";
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE);
    }

    public static void fileExtension() {
        String message = "File extension is not corrected!";
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE);
    }
}
