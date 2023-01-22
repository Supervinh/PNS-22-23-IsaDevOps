package interfaces;

import POJO.Admin;
import POJO.Store;
import interfaces.Exceptions.CredentialsException;

import java.util.Map;

public interface AdminDataGathering {

    Map<Store,Double> inquireConsumptionHabitsSells(Admin authorization) throws CredentialsException;
    Map<Store,Double> inquireConsumptionHabitsPayOffs(Admin authorization) throws CredentialsException;
}
