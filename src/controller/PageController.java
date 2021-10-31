package controller;

import model.ClientModel;
import view.CorrectedDataView;
import view.MainPanelView;
import view.PageControlView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PageController {
    public PageController(PageControlView pageControlView) {
        firstPage(pageControlView);
        lastPage(pageControlView);
        nextPage(pageControlView);
        prevPage(pageControlView);
        changeCountRowsOnPage(pageControlView);
    }

    public void firstPage(PageControlView pageControlView)//возврат к 1 стр
    {
        pageControlView.getFirstPageButton().addActionListener((event) -> {
            if(checkOnCorrectPerPage(pageControlView)) {
                if (pageControlView.getRecords() != 0) {
                    pageControlView.setPivotPage(1);
                    MainPanelView.deleteRows(pageControlView);
                    for (int i = 0; i < Integer.parseInt(pageControlView.getUpdatePerPageField().getText()); i++) {
                        if (i == pageControlView.getClients().size()) {
                            break;
                        }
                        copyData(i, pageControlView);
                        pageControlView.setPivotRecord(i);
                    }
                    pageControlView.setPivotPage(1);
                    pageControlView.getCurrentPageLabel().setText(pageControlView.getPivotPage()
                            + "/" + pageControlView.getCountPage());
                }
            }
        });
    }

    public void lastPage(PageControlView pageControlView)//возврат ко 2 стр
    {
        pageControlView.getLastPageButton().addActionListener((event) -> {
            if(checkOnCorrectPerPage(pageControlView)) {
                if (pageControlView.getRecords() != 0) {
                    pageControlView.setPivotPage(pageControlView.getCountPage());
                    MainPanelView.deleteRows(pageControlView);
                    for (int i = (pageControlView.getCountPage() - 1)
                            * Integer.parseInt(pageControlView.getUpdatePerPageField().getText()); i < pageControlView.getClients().size(); i++) {
                        if (i == pageControlView.getClients().size()) {
                            break;
                        }
                        copyData(i, pageControlView);
                        pageControlView.setPivotRecord(i);
                    }
                    pageControlView.setPivotPage(pageControlView.getCountPage());
                    pageControlView.getCurrentPageLabel().setText(pageControlView.getPivotPage() + "/" + pageControlView.getCountPage());
                }
            }
        });
    }

    public void nextPage(PageControlView pageControlView)//переход на след стр
    {
        pageControlView.getNextButton().addActionListener((event) -> {
            if(checkOnCorrectPerPage(pageControlView)) {
                if (pageControlView.getRecords() != 0) {
                    if (pageControlView.getPivotPage() < pageControlView.getCountPage()) {//переход на след стр если таково возможно
                        pageControlView.setPivotPage(pageControlView.getPivotPage() + 1);
                        pageControlView.getCurrentPageLabel().setText(pageControlView.getPivotPage() + "/" + pageControlView.getCountPage());
                    }
                    if (pageControlView.getPivotRecord() != pageControlView.getRecords() - 1)//текущая запись не последняя запись
                    {
                        int i;
                        MainPanelView.deleteRows(pageControlView);//чистка таблицы для вывода
                        for (i = pageControlView.getPivotRecord() + 1; i < pageControlView.getPivotPage()
                                * Integer.parseInt(pageControlView.getUpdatePerPageField().getText()); i++) {//вывод элементов след стр
                            if (i == pageControlView.getClients().size()) {
                                break;
                            }
                            copyData(i, pageControlView);
                        }
                        pageControlView.setPivotRecord(i - 1);//установка индекса тек записи
                    }
                }
            }
        });
    }

    public void prevPage(PageControlView pageControlView)//переход на пред стр
    {
        pageControlView.getPrevButton().addActionListener((event) -> {
            if(checkOnCorrectPerPage(pageControlView)) {
                if (pageControlView.getRecords() != 0) {
                    if (pageControlView.getPivotRecord() <= Integer.parseInt(pageControlView.getUpdatePerPageField().getText()) - 1) {
                        return;
                    }
                    if (pageControlView.getPivotRecord() != Integer.parseInt(pageControlView.getUpdatePerPageField().getText()) - 1) {
                        int i, countRecordsOnPivotPage;
                        MainPanelView.deleteRows(pageControlView);
                        countRecordsOnPivotPage = (pageControlView.getPivotRecord() + 1) % Integer.parseInt(pageControlView.getUpdatePerPageField().getText());
                        if (countRecordsOnPivotPage == 0) {
                            countRecordsOnPivotPage = Integer.parseInt(pageControlView.getUpdatePerPageField().getText());
                        }
                        for (i = pageControlView.getPivotRecord() - countRecordsOnPivotPage - Integer.parseInt(pageControlView.getUpdatePerPageField().getText()) + 1;
                             i <= pageControlView.getPivotRecord() - countRecordsOnPivotPage; i++) {
                            if (i == pageControlView.getClients().size()) {
                                break;
                            }
                            copyData(i, pageControlView);
                        }
                        pageControlView.setPivotRecord(i - 1);
                        if (pageControlView.getPivotPage() > 1) {
                            pageControlView.setPivotPage(pageControlView.getPivotPage() - 1);
                            pageControlView.getCurrentPageLabel().setText(pageControlView.getPivotPage() + "/" + pageControlView.getCountPage());
                        }
                    }
                }
            }
        });
    }

    private void changePerPage(PageControlView pageControlView) {
            if (pageControlView.getRecords() != 0) {
                int newPerPage = Integer.parseInt(pageControlView.getUpdatePerPageField().getText());
                if (pageControlView.getRecords() % newPerPage != 0) {
                    pageControlView.setCountPage(pageControlView.getRecords() / newPerPage + 1);
                } else {
                    pageControlView.setCountPage(pageControlView.getClients().size() / newPerPage);
                }
                pageControlView.getFirstPage(pageControlView);
            }
    }

    public static boolean checkOnCorrectPerPage(PageControlView pageControlView)
    {
        CorrectedDataController correctedDataController = new CorrectedDataController();
        if (!correctedDataController.isCorrectedPerPage(pageControlView.getUpdatePerPageField())) {
            CorrectedDataView.errorEnterPerPage();
            return false;
        }
        return true;
    }


    private void changeCountRowsOnPage(PageControlView pageControlView)//изменение кол-ва записей на стр
    {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Integer.parseInt(pageControlView.getUpdatePerPageField().getText());
                    changePerPage(pageControlView);
                }
                catch (Exception ignore) {
                    CorrectedDataView.errorEnterPerPage();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    Integer.parseInt(pageControlView.getUpdatePerPageField().getText());
                    changePerPage(pageControlView);
                }
                catch (Exception ignore) {
                    CorrectedDataView.errorEnterPerPage();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        pageControlView.getUpdatePerPageField().getDocument().addDocumentListener(listener);
    }


    public static void copyData(int i, PageControlView pageControlView)//заполнение таблицы для вывода в окне
    {
        List<String> params = new ArrayList<>(4);
        DefaultTableModel printTableModel = (DefaultTableModel) pageControlView.getPrintTable().getModel();
        ClientModel person = pageControlView.getClients().get(i);
        nullParams(person, params);
        if (person != null) {
            Object fio = person.getFio().getName() + " " + person.getFio().getSurname() + " " + person.getFio().getPatronymic();
            Object place = person.getPlaceResidence().getCountry() + " " + person.getPlaceResidence().getCity() + " "
                    + person.getPlaceResidence().getStreet() + " " + params.get(0) + " " + params.get(1);
            Object[] data = new Object[]{fio, person.getAccountNumber(), place, params.get(2), params.get(3)};
            printTableModel.addRow(data);
        }
    }

    private static void nullParams(ClientModel person, List<String> params) {
        String house, flat, mobilePhone, cityPhone;
        if (person.getPlaceResidence().getHouseNumber() == 0) {
            house = "";
        } else {
            house = Integer.toString(person.getPlaceResidence().getHouseNumber());
        }
        params.add(house);

        if (person.getPlaceResidence().getFlatNumber() == 0) {
            flat = "";
        } else {
            flat = Integer.toString(person.getPlaceResidence().getFlatNumber());
        }
        params.add(flat);

        if (person.getTelephone().getMobileTelephone() == 0) {
            mobilePhone = "";
        } else {
            mobilePhone = Integer.toString(person.getTelephone().getMobileTelephone());
        }
        params.add(mobilePhone);

        if (person.getTelephone().getCityTelephone() == 0) {
            cityPhone = "";
        } else {
            cityPhone = Integer.toString(person.getTelephone().getCityTelephone());
        }
        params.add(cityPhone);
    }
}
