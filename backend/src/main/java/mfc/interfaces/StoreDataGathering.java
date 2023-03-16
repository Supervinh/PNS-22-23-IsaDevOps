package mfc.interfaces;

import mfc.exceptions.CredentialsException;
import mfc.entities.Store;
import mfc.entities.StoreOwner;

import java.util.Map;

public interface StoreDataGathering {
    Map<String, Double> inquireOwnContribution(Store current, StoreOwner authorization) throws CredentialsException;

    Map<String, Double> inquireOwnSells(Store current, StoreOwner authorization) throws CredentialsException;
}
