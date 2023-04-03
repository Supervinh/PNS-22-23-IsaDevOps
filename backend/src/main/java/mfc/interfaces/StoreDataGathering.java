package mfc.interfaces;

import mfc.controllers.dto.DashboardDTO;
import mfc.entities.Store;

public interface StoreDataGathering {
    DashboardDTO gather(Store store);
}
