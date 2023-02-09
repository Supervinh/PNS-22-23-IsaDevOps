package mfc.interfaces;

import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.interfaces.Exceptions.CredentialsException;

import java.util.Map;

public interface StoreDataGathering {
    Map<String, Double> inquireOwnContribution(Store current, StoreOwner authorization) throws CredentialsException;

    Map<String, Double> inquireOwnSells(Store current, StoreOwner authorization) throws CredentialsException;
}
