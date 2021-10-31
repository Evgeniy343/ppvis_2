package controller;

import model.ClientModel;
import view.MainPanelView;
import view.PanelDeleteView;
import view.PanelSearchView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DeleteController {
    protected PanelDeleteView delete;

    DeleteController(MainPanelView view) {
        view.getDeleteButton().addActionListener((event) -> {
            if (PageController.checkOnCorrectPerPage(view.getPageControlView())) {
                delete = new PanelDeleteView(view);
            }
        });
        view.useMenu().getMenu(1).getItem(2).addActionListener((event) ->{
            if(PageController.checkOnCorrectPerPage(view.getPageControlView())) {
                delete = new PanelDeleteView(view);
            }
        });
    }


    public static void deletePersons(PanelDeleteView panelDelete, MainPanelView view) {
        panelDelete.getDelete().addActionListener((event) -> {
            panelDelete.getDeletePersons().addAll(PanelSearchView.getSearchPerson());
            if (!panelDelete.getDeletePersons().isEmpty()) {
                DeleteController.remove(panelDelete.getDeletePersons());
                numberOfDeletePersons();
                view.getPageControlView().setClients(ClientModel.getPersons());
                PanelSearchView.getSearchPerson().clear();
                view.getPageControlView().clearPageParams();
                for (int i = 0; i < ClientModel.getPersons().size(); i++) {
                    view.getPageControlView().updatePageParams();
                }
                view.getPageControlView().getFirstPage(view.getPageControlView());
                panelDelete.getDeletePersons().clear();
                if (panelDelete.getPanelSearch().getPageControlView().getRecords() != 0) {
                    for (int i = panelDelete.getPanelSearch().getPageControlView().getPrintTable().getRowCount() - 1; i >= 0; i--) {
                        DefaultTableModel model = (DefaultTableModel) panelDelete.getPanelSearch().getPageControlView().getPrintTable().getModel();
                        model.removeRow(i);
                    }
                    panelDelete.getPanelSearch().getPageControlView().clearPageParams();
                    if(view.getPageControlView().getRecords() == 0){ view.getPageControlView().clearPageParams(); }
                }
            }
        });
    }

    public static void numberOfDeletePersons() {
        String message;
        if (PanelSearchView.getSearchPerson().size() == 1) {
            message = "1 person - is deleted!";
        } else {
            message = PanelSearchView.getSearchPerson().size() + " persons - are deleted!";
        }
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.PLAIN_MESSAGE);
    }


    public static void remove(List<ClientModel> deletePerson) {
        for (ClientModel delete : deletePerson) {
            if (delete != null) {
                ClientModel.getPersons().remove(delete);
            }
        }
    }
}
