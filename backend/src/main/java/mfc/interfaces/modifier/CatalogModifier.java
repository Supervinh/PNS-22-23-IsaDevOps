package mfc.interfaces.modifier;

import mfc.entities.Payoff;
import mfc.entities.Store;
import mfc.exceptions.AlreadyExistingPayoffException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;

import java.util.Optional;

public interface CatalogModifier {
    Payoff addPayOff(String name, double cost, int pointCost, Store store, boolean isVfp) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException;

    Payoff editPayOff(Payoff payOff, Optional<Double> cost, Optional<Integer> pointCost, boolean isVfp) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException;

    void deletePayoff(Payoff payOff) throws PayoffNotFoundException;
}

