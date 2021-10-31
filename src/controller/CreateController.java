package controller;

import model.ClientModel;
import model.FIOModel;
import model.PlaceModel;
import model.TelephoneModel;
import view.CorrectedDataView;
import view.MainPanelView;
import view.PanelCreateView;

public class CreateController {
    protected PanelCreateView create;

    CreateController(MainPanelView view) {
        view.getCreateButton().addActionListener((event) -> {
            if (PageController.checkOnCorrectPerPage(view.getPageControlView())) {
                create = new PanelCreateView(view);
            }
    });
        view.useMenu().getMenu(1).getItem(0).addActionListener((event) -> {
            if (PageController.checkOnCorrectPerPage(view.getPageControlView())) {
                create = new PanelCreateView(view);
            }
    });
    }

    public static void createPerson(MainPanelView view) {
        PanelCreateView.getCreate().addActionListener((event) -> {
                if (CorrectedDataController.isAccountEmpty() || CorrectedDataController.isFIOEmpty()
                        || CorrectedDataController.isAddressEmpty() || CorrectedDataController.isTelephoneEmpty()) {
                    CorrectedDataView.enterData();
                } else {
                    CorrectedDataController correctedData = new CorrectedDataController();
                    TelephoneModel telephone;
                    PlaceModel placeResidence;
                    if (correctedData.isCorrectedData(PanelCreateView.getTelephoneTextField(), PanelCreateView.getPlaceResidenceTextField())) {
                        telephone = new TelephoneModel(PanelCreateView.getTelephoneTextField());
                        placeResidence = new PlaceModel(PanelCreateView.getPlaceResidenceTextField());
                    } else {
                        CorrectedDataView.errorEnter();
                        return;
                    }
                    FIOModel fio = new FIOModel(PanelCreateView.getFioTextField());
                    String accountNumber = PanelCreateView.getAccountNumberTextField().getText();
                    ClientModel person = new ClientModel(fio, accountNumber, placeResidence, telephone);
                    ClientModel.getPersons().add(person);
                    view.getPageControlView().updatePageParams();
                    view.getPageControlView().getFirstPage(view.getPageControlView());
                }
        });
    }
}
