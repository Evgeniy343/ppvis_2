package parser;

import controller.PageController;
import model.ClientModel;
import model.FIOModel;
import model.PlaceModel;
import model.TelephoneModel;
import view.CorrectedDataView;
import view.MainPanelView;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXReader {
    public SAXReader(MainPanelView view) {
        final File[] file = new File[1];
        view.getOpenButton().addActionListener((event) -> readFile(view, file));
        view.useMenu().getMenu(0).getItem(0).addActionListener((event) -> readFile(view, file));
    }

    private void readFile(MainPanelView view, File[] file) {
        if(PageController.checkOnCorrectPerPage(view.getPageControlView())) {
            String fileName = getOpenFileName(view, file);
            if (!fileName.equals("")) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = null;
                try {
                    parser = factory.newSAXParser();
                } catch (ParserConfigurationException | SAXException e) {
                    e.printStackTrace();
                }
                XMLHandler handler = new XMLHandler();
                try {
                    assert parser != null;
                    parser.parse(new File(fileName), handler);
                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }
                view.getPageControlView().clearPageParams();
                view.getPageControlView().setClients(ClientModel.getPersons());
                for (int i = 0; i < ClientModel.getPersons().size(); i++) {
                    view.getPageControlView().updatePageParams();
                }
                view.getPageControlView().getFirstPage(view.getPageControlView());
            }
        }
    }

    private String getOpenFileName(MainPanelView view, File[] file) {
        file[0] = null;
        view.initFileChooser();
        view.getFileChooser().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = view.getFileChooser().showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            file[0] = view.getFileChooser().getSelectedFile();
        }
        if (file[0] == null) {
            return "";
        }
        if(!file[0].getName().contains(".xml")){
            CorrectedDataView.fileExtension();
            return getOpenFileName(view,file);
        }
        return file[0].getName();
    }

    private static class XMLHandler extends DefaultHandler {
        private FIOModel fio;
        private String accountNumber;
        private PlaceModel place;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {

            if (qName.equals("FIO")) {
                fio = new FIOModel(attributes.getValue("name"), attributes.getValue("surname"), attributes.getValue("patronymic"));
            }
            if (qName.equals("account")) {
                accountNumber = attributes.getValue("account");
            }
            if (qName.equals("address")) {
                String house = attributes.getValue("house");
                String flat = attributes.getValue("flat");
                place = new PlaceModel(attributes.getValue("country"), attributes.getValue("city")
                        , attributes.getValue("street"), house, flat);
            }
            if (qName.equals("telephone")) {
                String mobile = attributes.getValue("mobilePhone");
                String city = attributes.getValue("cityPhone");
                TelephoneModel telephone = new TelephoneModel(mobile, city);
                ClientModel.getPersons().add(new ClientModel(fio, accountNumber, place, telephone));
            }
        }
    }
}
