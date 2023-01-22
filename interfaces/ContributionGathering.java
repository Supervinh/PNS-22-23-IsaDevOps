package interfaces;

import POJO.Store;

import java.util.Map;

public interface ContributionGathering {

    Map<String,Double> inquireComparaisonContribution(Store current);
    Map<String,Double> inquireComparaisonSell(Store current);


}
