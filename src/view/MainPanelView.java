package view;

import model.ClientModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainPanelView extends JFrame {
    private JButton deleteButton;
    private JButton searchButton;
    private JButton createButton;
    private JButton openButton;
    private JButton saveButton;

    private JPanel pagePanel;
    private JPanel editPanel;
    private JPanel contentPane;

    private JMenuBar menuBar;

    private JScrollPane scrollPane;

    private JFileChooser fileChooser;

    private final PageControlView pageControlView;

    public MainPanelView() {
        this.setTitle("MainPanel");
        pageControlView = new PageControlView();
        pageControlView.setClients(ClientModel.getPersons());
        initComponents(pageControlView);
        setMenuBar();
        setEditPanel();
        setContentPane();
        pagePanel.add(pageControlView.getPageControlPanel(), BorderLayout.SOUTH);
        this.add(contentPane);
        showWindow();
    }

    private void initComponents(PageControlView pageControlView) {
        initPanels();
        initButtons();
        scrollPane = new JScrollPane(pageControlView.getPrintTable());
        menuBar = new JMenuBar();
    }

    private void initButtons() {
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        createButton = new JButton("Create");
        openButton = new JButton("Open");
        saveButton = new JButton("Save");
    }

    private void initPanels() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pagePanel = new JPanel(new BorderLayout());
        editPanel = new JPanel(new GridLayout(15, 1, 0, 10));
    }

    private void showWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (screenSize.width - 1200) / 2;
        int locationY = (screenSize.height - 700) / 2;
        this.setBounds(locationX, locationY, 1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setEditPanel() {
        editPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        editPanel.add(createButton);
        editPanel.add(searchButton);
        editPanel.add(deleteButton);
        editPanel.add(new JSeparator());
        editPanel.add(openButton);
        editPanel.add(saveButton);
    }

    private void setContentPane() {
        contentPane.add(pagePanel, BorderLayout.SOUTH);
        contentPane.add(editPanel, BorderLayout.WEST);
        contentPane.add(menuBar, BorderLayout.PAGE_START);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    public void setMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem createItem = new JMenuItem("Create");
        JMenuItem searchItem = new JMenuItem("Search");
        JMenuItem deleteItem = new JMenuItem("Delete");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        editMenu.add(createItem);
        editMenu.add(searchItem);
        editMenu.add(deleteItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

    public static void deleteRows(PageControlView pageControlView) {
        for (int i = pageControlView.getPrintTable().getRowCount() - 1; i != -1; i--) {
            DefaultTableModel printTableModel = (DefaultTableModel) pageControlView.getPrintTable().getModel();
            printTableModel.removeRow(i);
        }
    }

    public void initFileChooser() {
        fileChooser = new JFileChooser(".");
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getOpenButton() {
        return openButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public PageControlView getPageControlView() { return pageControlView; }

    public JMenuBar useMenu() {
        return menuBar;
    }
}
