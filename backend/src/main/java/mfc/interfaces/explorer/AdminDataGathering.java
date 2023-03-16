package mfc.interfaces.explorer;

import mfc.entities.Admin;
import mfc.entities.Store;

import java.util.Map;

public interface AdminDataGathering {
    Map<Store, Double> inquireConsumptionHabitsSells(Admin authorization);

    Map<Store, Double> inquireConsumptionHabitsPayOffs(Admin authorization);
}

