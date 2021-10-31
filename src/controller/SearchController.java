package controller;

import model.ClientModel;
import view.CorrectedDataView;
import view.MainPanelView;
import view.PanelSearchView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SearchController {
    protected PanelSearchView search;

    SearchController(MainPanelView view) {
        view.getSearchButton().addActionListener((event) -> {
            if (PageController.checkOnCorrectPerPage(view.getPageControlView())) {
                search = new PanelSearchView(null);
            }
        });
        view.useMenu().getMenu(1).getItem(1).addActionListener((event) -> {
            if (PageController.checkOnCorrectPerPage(view.getPageControlView())) {
                search = new PanelSearchView(null);
            }
        });
    }


    public static void searchData(PanelSearchView panelSearch) {
        PanelSearchView.getSearchPerson().clear();
        panelSearch.getSearch().addActionListener((event) -> {
            if(PageController.checkOnCorrectPerPage(panelSearch.getPageControlView())) {
                if (panelSearch.getPageControlView().getRecords() != 0) {
                    for (int i = panelSearch.getPageControlView().getPrintTable().getRowCount() - 1; i >= 0; i--) {
                        DefaultTableModel model = (DefaultTableModel) panelSearch.getPageControlView().getPrintTable().getModel();
                        model.removeRow(i);
                    }
                    panelSearch.getPageControlView().clearPageParams();
                }
                boolean isCorrected;
                isCorrected = panelSearch.chooseSearchPath();
                if (!isCorrected) {
                    CorrectedDataView.errorEnter();
                } else {
                    panelSearch.getPageControlView().setClients(PanelSearchView.getSearchPerson());
                    for (int i = 0; i < PanelSearchView.getSearchPerson().size(); i++) {
                        panelSearch.getPageControlView().updatePageParams();
                        panelSearch.getPageControlView().getFirstPage(panelSearch.getPageControlView());
                    }
                }
            }
        });
    }


    public static void searchFioOrTelephone(JTextField[] fio, JTextField[] telephone, List<ClientModel> result) {
        String mobile = telephone[0].getText();
        if (mobile.equals("")) {
            mobile = "0";
        }
        for (ClientModel person : ClientModel.getPersons()) {
            if (person.getFio().getSurname().equals(fio[1].getText())
                    || person.getTelephone().getMobileTelephone() == Integer.parseInt(mobile)) {
                if(CorrectedDataController.isFIOEmpty() && CorrectedDataController.isTelephoneEmpty()){ continue; }
                result.add(person);
            }
        }
    }

    public static void searchAccountOrPlace(JTextField account, JTextField[] place, List<ClientModel> result) {
        String house = place[3].getText();
        if (house.equals("")) {
            house = "0";
        }
        String flat = place[4].getText();
        if (flat.equals("")) {
            flat = "0";
        }
        for (ClientModel person : ClientModel.getPersons()) {
            if (person.getAccountNumber().equals(account.getText())
                    || (person.getPlaceResidence().getCountry().equals(place[0].getText())
                    && person.getPlaceResidence().getCity().equals(place[1].getText())
                    && person.getPlaceResidence().getStreet().equals(place[2].getText())
                    && person.getPlaceResidence().getHouseNumber() == Integer.parseInt(house)
                    && person.getPlaceResidence().getFlatNumber() == Integer.parseInt(flat))) {
                if(CorrectedDataController.isAddressEmpty() && CorrectedDataController.isAccountEmpty()){ continue; }
                result.add(person);
            }
        }
    }

    public static void searchFioOrNumbersOfTelephone(JTextField[] fio, JTextField[] telephone, List<ClientModel> result) {
        String mobile = telephone[0].getText();
        if (mobile.equals("")) {
            mobile = "0";
        }
        String city = telephone[1].getText();
        if (city.equals("")) {
            city = "0";
        }
        for (ClientModel person : ClientModel.getPersons()) {
            int telMob = person.getTelephone().getMobileTelephone();
            int telCity = person.getTelephone().getCityTelephone();
            if ((person.getFio().getName().equals(fio[0].getText()) || person.getFio().getSurname().equals(fio[1].getText())
                    || person.getFio().getPatronymic().equals(fio[2].getText()))
                    && (Integer.toString(telMob).contains(mobile)
                    || Integer.toString(telCity).contains(city))) {
                if(CorrectedDataController.isFIOEmpty() && CorrectedDataController.isTelephoneEmpty()){ continue; }
                result.add(person);
            }
        }
    }
}
