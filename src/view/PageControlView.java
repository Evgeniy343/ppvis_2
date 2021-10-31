package view;

import controller.PageController;
import model.ClientModel;
import tableHeader.ColumnGroup;
import tableHeader.GroupableTableHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.List;

public class PageControlView {

    private JButton nextButton;
    private JButton prevButton;
    private JButton lastPageButton;
    private JButton firstPageButton;

    private JLabel updatePerPageLabel;
    private JLabel currentPageLabel;
    private JLabel recordsLabel;

    private int countPage;
    private int pivotPage;
    private int pivotRecord;
    private int records;
    private int perPage;

    private JTextField updatePerPageField;

    private JPanel pageControlPanel;

    private JTable printTable;

    private final Object[] headers = {"FIO client", "Account number", "Place residence", "Mobile", "City"};

    private List<ClientModel> clients = new ArrayList<>(100);

    protected PageController pageController;

    PageControlView() {
        initComponents();
        setPageControlPanel();
        pageController = new PageController(this);
    }


    private void initComponents() {
        pageControlPanel = new JPanel();
        initButtons();
        initPageParameters();
        updatePerPageField = new JTextField(String.valueOf(perPage), 4);
        initLabels();
        DefaultTableModel tableModel = new DefaultTableModel(headers, 0);
        printTable = new JTable(tableModel) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };


        TableColumnModel columnModel = printTable.getColumnModel();
        ColumnGroup g_name = new ColumnGroup("Telephone");
        g_name.add(columnModel.getColumn(3));
        g_name.add(columnModel.getColumn(4));
        GroupableTableHeader header = (GroupableTableHeader) printTable.getTableHeader();
        header.addColumnGroup(g_name);
    }

    private void initButtons() {
        nextButton = new JButton(">");
        prevButton = new JButton("<");
        lastPageButton = new JButton("Last");
        firstPageButton = new JButton("First");
    }

    private void initLabels() {
        updatePerPageLabel = new JLabel("Per page:");
        currentPageLabel = new JLabel(pivotPage + "/" + countPage);
        recordsLabel = new JLabel("Records: " + records);
    }

    private void initPageParameters() {
        pivotRecord = 0;
        pivotPage = 0;
        countPage = 0;
        records = 0;
        perPage = 5;
    }

    private void setPageControlPanel() {
        pageControlPanel.add(updatePerPageLabel);
        pageControlPanel.add(updatePerPageField);
        pageControlPanel.add(firstPageButton);
        pageControlPanel.add(prevButton);
        pageControlPanel.add(currentPageLabel);
        pageControlPanel.add(nextButton);
        pageControlPanel.add(lastPageButton);
        pageControlPanel.add(recordsLabel);
    }

    public void getFirstPage(PageControlView pageControlView) {
        pageControlView.setPivotPage(1);
        pageControlView.getCurrentPageLabel().setText(pageControlView.getPivotPage() + "/" + pageControlView.getCountPage());
        MainPanelView.deleteRows(pageControlView);
        int i;
        for (i = 0; i < Integer.parseInt(pageControlView.getUpdatePerPageField().getText()); i++) {
            if (i == clients.size()) {
                break;
            }
            PageController.copyData(i, pageControlView);
        }
        pageControlView.setPivotRecord(i - 1);
    }

    public void updatePageParams()//обновление таблицы
    {
        records = records + 1;
        if (records > Integer.parseInt(updatePerPageField.getText()) * countPage) {
            countPage = countPage + 1;
            pivotPage = 1;
            currentPageLabel.setText(pivotPage + "/" + countPage);
        }
        recordsLabel.setText("Records: " + records);
        pageControlPanel.add(recordsLabel);
    }


    public void clearPageParams()//**
    {
        pivotPage = 0;
        countPage = 0;
        records = 0;
        pivotRecord = 0;
        currentPageLabel.setText(pivotPage + "/" + countPage);
        recordsLabel.setText("Records 0");
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getLastPageButton() {
        return lastPageButton;
    }

    public JButton getFirstPageButton() {
        return firstPageButton;
    }

    public JTextField getUpdatePerPageField() {
        return updatePerPageField;
    }

    public int getPerPage() {
        return perPage;
    }

    public JLabel getCurrentPageLabel() {
        return currentPageLabel;
    }

    public JPanel getPageControlPanel() { return pageControlPanel; }

    public JTable getPrintTable() {
        return printTable;
    }

    public int getCountPage() {
        return countPage;
    }

    public int getPivotPage() {
        return pivotPage;
    }

    public int getPivotRecord() {
        return pivotRecord;
    }

    public int getRecords() {
        return records;
    }

    public void setPivotRecord(int page) {
        pivotRecord = page;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public void setPivotPage(int pivotPage) {
        this.pivotPage = pivotPage;
    }

    public void setClients(List<ClientModel> clients) { this.clients = clients; }

    public List<ClientModel> getClients() {
        return clients;
    }
}
