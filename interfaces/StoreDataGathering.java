package interfaces;

import POJO.Store;
import POJO.StoreOwner;
import interfaces.Exceptions.CredentialsException;

import java.util.Map;

public interface StoreDataGathering {
    Map<String, Double> inquireOwnContribution(Store current, StoreOwner authorization) throws CredentialsException;

    Map<String, Double> inquireOwnSells(Store current, StoreOwner authorization) throws CredentialsException;
}
