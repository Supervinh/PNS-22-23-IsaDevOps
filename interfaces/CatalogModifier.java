package interfaces;

import POJO.Admin;
import POJO.PayOff;
import POJO.Store;
import POJO.StoreOwner;
import interfaces.Exceptions.CredentialsException;
import interfaces.Exceptions.NegativeCostException;
import interfaces.Exceptions.NegativePointCostException;

public interface CatalogModifier {
    boolean addPayOff(String name, double cost, int pointCost, Store store, StoreOwner authorization) throws NegativeCostException, NegativePointCostException, CredentialsException;

    boolean editPayOff(PayOff payOff, Store store, double cost, int pointCost, StoreOwner authorization) throws NegativeCostException, NegativePointCostException, CredentialsException;

    boolean editPayOff(PayOff payOff, Store store, double cost, StoreOwner authorization) throws NegativeCostException, CredentialsException;

    boolean editPayOff(PayOff payOff, Store store, int pointCost, StoreOwner authorization) throws NegativePointCostException, CredentialsException;

    boolean addPayOffAdmin(String name, double cost, int pointCost, Admin authorization) throws NegativeCostException, NegativePointCostException;

    boolean editPayOff(PayOff payOff, Store store, double cost, int pointCost, Admin authorization) throws NegativeCostException, NegativePointCostException;

    boolean editPayOff(PayOff payOff, Store store, double cost, Admin authorization) throws NegativeCostException;

    boolean editPayOff(PayOff payOff, Store store, int pointCost, Admin authorization) throws NegativePointCostException;
}

