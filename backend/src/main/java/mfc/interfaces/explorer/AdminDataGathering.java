package mfc.interfaces.explorer;

import mfc.pojo.Admin;
import mfc.pojo.Store;

import java.util.Map;

public interface AdminDataGathering {
    Map<Store, Double> inquireConsumptionHabitsSells(Admin authorization);

    Map<Store, Double> inquireConsumptionHabitsPayOffs(Admin authorization);
}

