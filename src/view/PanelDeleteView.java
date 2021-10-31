package view;

import controller.DeleteController;
import model.ClientModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelDeleteView extends PanelView {

    private final List<ClientModel> deletePersons;
    private final JButton delete;
    private final PanelSearchView panelSearch;

    public PanelDeleteView(MainPanelView view) {
        deletePersons = new ArrayList<>(100);
        delete = new JButton("Delete");
        panelSearch = new PanelSearchView(delete);
        DeleteController.deletePersons(this, view);
    }

    public JButton getDelete() {
        return delete;
    }

    public List<ClientModel> getDeletePersons() {
        return deletePersons;
    }

    public PanelSearchView getPanelSearch() {
        return panelSearch;
    }

}
