package parser;

import model.ClientModel;
import view.CorrectedDataView;
import view.MainPanelView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DOMWriter {
    public DOMWriter(MainPanelView view) {
        final File[] file = new File[1];
        view.getSaveButton().addActionListener((event) -> writeInFile(view, file));
        view.useMenu().getMenu(0).getItem(1).addActionListener((event) -> writeInFile(view, file));
    }

    private void writeInFile(MainPanelView view, File[] file)//**
    {
        String fileName = getSaveFileName(view, file);
        if (fileName.equals("")) {
            return;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert builder != null;

        Document document = null;
        if (!file[0].exists()) {
            document = builder.newDocument();
            createClientNodesInNewFile(document);
        } else {
            try {
                document = builder.parse(file[0]);
                createClientNodesInExistingFile(document);
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(fileName));
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private String getSaveFileName(MainPanelView view, File[] file) {
        file[0] = null;
        view.initFileChooser();
        view.getFileChooser().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = view.getFileChooser().showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            file[0] = view.getFileChooser().getSelectedFile();
        }
        if (file[0] == null) {
            return "";
        }
        if(!file[0].getName().contains(".xml")){
            CorrectedDataView.fileExtension();
            return getSaveFileName(view,file);
        }
        return file[0].getName();
    }

    private void createClientNodesInExistingFile(Document document) {
        NodeList clients = document.getElementsByTagName("clients");
        Node nClients = clients.item(0);
        for (ClientModel person : ClientModel.getPersons()) {
            Element client = document.createElement("client");
            createFIONode(person, document, client);
            createAccountNode(person, document, client);
            createAddressNode(person, document, client);
            createTelephoneNode(person, document, client);
            nClients.appendChild(client);
        }
    }

    private void createClientNodesInNewFile(Document document) {
        Element company, clients;
        company = document.createElement("company");
        document.appendChild(company);
        clients = document.createElement("clients");
        company.appendChild(clients);
        for (ClientModel person : ClientModel.getPersons()) {
            Element client = document.createElement("client");
            createFIONode(person, document, client);
            createAccountNode(person, document, client);
            createAddressNode(person, document, client);
            createTelephoneNode(person, document, client);
            clients.appendChild(client);
        }
    }

    private void createFIONode(ClientModel person, Document document, Element client) {
        Element FIO = document.createElement("FIO");
        FIO.setAttribute("name", person.getFio().getName());
        FIO.setAttribute("surname", person.getFio().getSurname());
        FIO.setAttribute("patronymic", person.getFio().getPatronymic());
        client.appendChild(FIO);
    }

    private void createAddressNode(ClientModel person, Document document, Element client) {
        Element address = document.createElement("address");
        address.setAttribute("country", person.getPlaceResidence().getCountry());
        address.setAttribute("city", person.getPlaceResidence().getCity());
        address.setAttribute("street", person.getPlaceResidence().getStreet());
        address.setAttribute("house", Integer.toString(person.getPlaceResidence().getHouseNumber()));
        address.setAttribute("flat", Integer.toString(person.getPlaceResidence().getFlatNumber()));
        client.appendChild(address);
    }

    private void createAccountNode(ClientModel person, Document document, Element client) {
        Element account = document.createElement("account");
        account.setAttribute("account", person.getAccountNumber());
        client.appendChild(account);
    }

    private void createTelephoneNode(ClientModel person, Document document, Element client) {
        Element telephone = document.createElement("telephone");
        telephone.setAttribute("mobilePhone", Integer.toString(person.getTelephone().getMobileTelephone()));
        telephone.setAttribute("cityPhone", Integer.toString(person.getTelephone().getCityTelephone()));
        client.appendChild(telephone);
    }

}
