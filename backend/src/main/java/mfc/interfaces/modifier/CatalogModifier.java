package mfc.interfaces.modifier;

import mfc.POJO.PayOff;
import mfc.POJO.Store;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.AlreadyExistingPayoffException;
import mfc.exceptions.PayoffNotFoundException;

public interface CatalogModifier {
    PayOff addPayOff(String name, double cost, int pointCost, Store store) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException;

    PayOff editPayOff(PayOff payOff, Store store, double cost, int pointCost) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException;

    PayOff editPayOff(PayOff payOff, Store store, double cost) throws NegativeCostException, PayoffNotFoundException;

    PayOff editPayOff(PayOff payOff, Store store, int pointCost) throws NegativePointCostException, PayoffNotFoundException;
}

