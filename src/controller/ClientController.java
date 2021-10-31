package controller;

import parser.DOMWriter;
import parser.SAXReader;
import view.MainPanelView;

public class ClientController {
    protected CreateController createController;
    protected SearchController searchController;
    protected DeleteController deleteController;
    protected SAXReader saxReader;
    protected DOMWriter domWriter;

    public ClientController() {
        MainPanelView view = new MainPanelView();
        initParsers(view);
        initControllers(view);
    }

    private void initControllers(MainPanelView view) {
        createController = new CreateController(view);
        searchController = new SearchController(view);
        deleteController = new DeleteController(view);
    }

    private void initParsers(MainPanelView view) {
        saxReader = new SAXReader(view);
        domWriter = new DOMWriter(view);
    }

}
