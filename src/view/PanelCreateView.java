package view;

import controller.CreateController;

import javax.swing.*;
import java.awt.*;

public class PanelCreateView extends PanelView {

    private static JButton create;
    private final JPanel createPanel;

    public PanelCreateView(MainPanelView view) {
        this.setTitle("CreatePanel");
        create = new JButton("Create");
        createPanel = new JPanel(new GridLayout(12, 2, 5, 5));
        createPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
        initTextFields();
        setModelComponents();
        this.add(createPanel);
        showWindow();
        CreateController.createPerson(view);
    }

    private void setModelComponents() {
        addFioOnPanel(createPanel);
        addAccountOnPanel(createPanel);
        addTelephoneOnPanel(createPanel);
        addPlaceOnPanel(createPanel);
        createPanel.add(create);
    }

    private void showWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (screenSize.width - 500) / 2;
        int locationY = (screenSize.height - 500) / 2;
        this.setBounds(locationX, locationY, 500, 500);
        this.setVisible(true);
    }

    public static JButton getCreate() {
        return create;
    }
}
