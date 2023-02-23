package mfc.interfaces.modifier;

import mfc.POJO.PayOff;
import mfc.POJO.Store;
import mfc.exceptions.AlreadyExistingPayoffException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;

import java.util.Optional;

public interface CatalogModifier {
    PayOff addPayOff(String name, double cost, int pointCost, Store store) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException;

    PayOff editPayOff(PayOff payOff, Optional<Double> cost, Optional<Integer> pointCost) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException;

    PayOff deletePayoff(PayOff payOff) throws PayoffNotFoundException;
}

