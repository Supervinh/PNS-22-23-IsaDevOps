package interfaces;

import POJO.Admin;
import POJO.Store;

import java.util.Map;

public interface AdminDataGathering {
    Map<Store, Double> inquireConsumptionHabitsSells(Admin authorization);

    Map<Store, Double> inquireConsumptionHabitsPayOffs(Admin authorization);
}

