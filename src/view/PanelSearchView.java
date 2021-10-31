package view;

import controller.CorrectedDataController;
import controller.SearchController;
import model.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelSearchView extends PanelView {

    private JButton search;
    private JButton choice;

    private static List<ClientModel> searchPersons;

    private final JScrollPane scrollPane;

    private JPanel editingPanel;
    private JPanel searchPanel;
    private JPanel contentPanel;
    private JPanel pagePanel;

    private final PageControlView pageControlView;

    public PanelSearchView(JButton delete) {
        this.setTitle("SearchPanel");
        initButtons();
        searchPersons = new ArrayList<>(100);
        initComponents();
        initTextFields();
        pageControlView = new PageControlView();
        pageControlView.setClients(searchPersons);
        scrollPane = new JScrollPane(pageControlView.getPrintTable());
        initPanels();
        addButtonsOnEditPanel(delete);
        addSearchComponentsOnSearchPanel();
        setContentPanel();
        SearchController.searchData(this);
        this.add(contentPanel);
        showWindow();
    }

    private void initButtons() {
        search = new JButton("search");
        choice = new JButton("choice");
    }

    private void initPanels() {
        pagePanel = new JPanel(new BorderLayout());
        pagePanel.add(pageControlView.getPageControlPanel(), BorderLayout.SOUTH);
        editingPanel = new JPanel(new GridLayout(20, 1, 0, 10));
        editingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        searchPanel = new JPanel(new GridLayout(12, 2, 5, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void showWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (screenSize.width - 1200) / 2;
        int locationY = (screenSize.height - 700) / 2;
        this.setBounds(locationX, locationY, 1200, 700);
        this.setVisible(true);
    }

    private void setContentPanel() {
        contentPanel.add(pagePanel, BorderLayout.SOUTH);
        contentPanel.add(searchPanel, BorderLayout.EAST);
        contentPanel.add(editingPanel, BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void addButtonsOnEditPanel(JButton delete) {//добавление кнопок управления на панели
        ButtonGroup checkGroup = new ButtonGroup();
        for (JCheckBox jCheckBox : buttonSearch) {
            checkGroup.add(jCheckBox);
            editingPanel.add(jCheckBox);
        }
        editingPanel.add(choice);
        editingPanel.add(search);
        if (delete != null) {
            this.setTitle("DeletePanel");
            editingPanel.add(delete);
        }
    }

    private void addSearchComponentsOnSearchPanel() {//выбор компонентов поиска
        this.choice.addActionListener((event) -> {
            searchPanel.removeAll();
            this.getPageControlView().getPrintTable().removeAll();
            if (buttonSearch[0].isSelected()) {
                addFirstSearchComponents();
            }
            if (buttonSearch[1].isSelected()) {
                addSecondSearchComponents();
            }
            if (buttonSearch[2].isSelected()) {
                addThirdSearchComponents();
            }
            updatePanel();
        });
    }

    private void addFirstSearchComponents() {//компоненты для поиска 1
        searchPanel.add(fioLabel[1]);
        searchPanel.add(getFioTextField()[1]);
        searchPanel.add(telephoneLabel[0]);
        searchPanel.add(getTelephoneTextField()[0]);
    }

    private void addSecondSearchComponents() {//компоненты для поиска 2
        searchPanel.add(accountNumberLabel);
        searchPanel.add(getAccountNumberTextField());
        addPlaceOnPanel(searchPanel);
    }

    private void addThirdSearchComponents() {//компоненты для поиска 3
        addFioOnPanel(searchPanel);
        addTelephoneOnPanel(searchPanel);
    }

    public boolean chooseSearchPath()//выбор способа поиска с проверкой на корректность данных?
    {
        searchPersons.clear();
        CorrectedDataController correctedData = new CorrectedDataController();
        if (buttonSearch[0].isSelected()) {
            if (correctedData.isCorrectedTelephone(getTelephoneTextField(), true)) {
                firstSearchPath();
            } else {
                return false;
            }
        }
        if (buttonSearch[1].isSelected()) {
            if (correctedData.isCorrectedPlace(getPlaceResidenceTextField(), true)) {
                secondSearchPath();
            } else {
                return false;
            }
        }
        if (buttonSearch[2].isSelected()) {
            if (correctedData.isCorrectedTelephone(getTelephoneTextField(), true)) {
                thirdSearchPath();
            } else {
                return false;
            }
        }
        return true;
    }

    private void firstSearchPath()//поиск 1
    {
        SearchController.searchFioOrTelephone(getFioTextField(), getTelephoneTextField(), searchPersons);
        if (searchPersons.isEmpty()) {
            CorrectedDataView.errorFind();
        }
    }

    private void secondSearchPath()//поиск 2
    {
        SearchController.searchAccountOrPlace(getAccountNumberTextField(), getPlaceResidenceTextField(), searchPersons);
        if (searchPersons.isEmpty()) {
            CorrectedDataView.errorFind();
        }
    }

    private void thirdSearchPath()//поиск 3
    {
        SearchController.searchFioOrNumbersOfTelephone(getFioTextField(), getTelephoneTextField(), searchPersons);
        if (searchPersons.isEmpty()) {
            CorrectedDataView.errorFind();
        }
    }


    public static List<ClientModel> getSearchPerson() {
        return searchPersons;
    }

    public JButton getSearch() {
        return search;
    }

    public PageControlView getPageControlView() {
        return pageControlView;
    }

    public void updatePanel()
    {
        this.revalidate();
        this.repaint();
    }
}
