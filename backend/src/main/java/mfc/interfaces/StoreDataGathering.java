package mfc.interfaces;

import mfc.exceptions.CredentialsException;
import mfc.pojo.Store;
import mfc.pojo.StoreOwner;

import java.util.Map;

public interface StoreDataGathering {
    Map<String, Double> inquireOwnContribution(Store current, StoreOwner authorization) throws CredentialsException;

    Map<String, Double> inquireOwnSells(Store current, StoreOwner authorization) throws CredentialsException;
}
