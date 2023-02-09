package mfc.interfaces;

import mfc.POJO.Store;

import java.util.Map;

public interface ContributionGathering {
    Map<String, Double> inquireComparisonContribution(Store current);

    Map<String, Double> inquireComparisonSell(Store current);
}
