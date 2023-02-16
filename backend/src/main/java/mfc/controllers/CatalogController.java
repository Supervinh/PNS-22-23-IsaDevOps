package mfc.controllers;

import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import org.springframework.beans.factory.annotation.Autowired;

public class CatalogController {
    @Autowired
    private CatalogExplorer catalogExplorer;

    @Autowired
    private CustomerFinder customerFinder;


}
