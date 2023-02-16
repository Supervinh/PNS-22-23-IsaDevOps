package mfc.interfaces.explorer;

import mfc.POJO.Admin;
import mfc.POJO.Store;

import java.util.Map;

public interface AdminDataGathering {
    Map<Store, Double> inquireConsumptionHabitsSells(Admin authorization);

    Map<Store, Double> inquireConsumptionHabitsPayOffs(Admin authorization);
}

