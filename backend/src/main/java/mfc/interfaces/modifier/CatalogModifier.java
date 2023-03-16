package mfc.interfaces.modifier;

import mfc.exceptions.AlreadyExistingPayoffException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.pojo.Payoff;
import mfc.pojo.Store;

import java.util.Optional;

public interface CatalogModifier {
    Payoff addPayOff(String name, double cost, int pointCost, Store store) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException;

    Payoff editPayOff(Payoff payOff, Optional<Double> cost, Optional<Integer> pointCost) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException;

    Payoff deletePayoff(Payoff payOff) throws PayoffNotFoundException;
}

