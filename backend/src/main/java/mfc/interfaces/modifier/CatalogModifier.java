package mfc.interfaces.modifier;

import mfc.POJO.Admin;
import mfc.POJO.PayOff;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.interfaces.exceptions.CredentialsException;
import mfc.interfaces.exceptions.NegativeCostException;
import mfc.interfaces.exceptions.NegativePointCostException;

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

