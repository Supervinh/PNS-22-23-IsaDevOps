package cli.model;

public class CliDashboard {
    private double numberOfCustomers;
    private double numberOfSales;
    private double numberOfGivenPayoffs;
    private double salesVolumes;
    private double payoffCumulatedCost;

    public CliDashboard(double numberOfCustomers, double numberOfSales, double numberOfGivenPayoffs, double salesVolumes, double payoffCumulatedCost) {
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfSales = numberOfSales;
        this.numberOfGivenPayoffs = numberOfGivenPayoffs;
        this.salesVolumes = salesVolumes;
        this.payoffCumulatedCost = payoffCumulatedCost;
    }

    public CliDashboard() {
    }

    public double getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(double numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public double getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(double numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public double getSalesVolumes() {
        return salesVolumes;
    }

    public void setSalesVolumes(double salesVolumes) {
        this.salesVolumes = salesVolumes;
    }

    public double getPayoffCumulatedCost() {
        return payoffCumulatedCost;
    }

    public void setPayoffCumulatedCost(double payoffCumulatedCost) {
        this.payoffCumulatedCost = payoffCumulatedCost;
    }

    public double getNumberOfGivenPayoffs() {
        return numberOfGivenPayoffs;
    }

    public void setNumberOfGivenPayoffs(double numberOfGivenPayoffs) {
        this.numberOfGivenPayoffs = numberOfGivenPayoffs;
    }

    @Override
    public String toString() {
        return "\tDashboard of this store\t\n" +
                "------------------------------------" +
                "\nDistinct Customers :\t" + numberOfCustomers +
                "\nSales :\t" + numberOfSales +
                "\nGiven payoffs :\t" + numberOfGivenPayoffs +
                "\nSales volumes :\t" + salesVolumes +
                "\nCost of payoffs :\t" + payoffCumulatedCost +
                "\n------------------------------------" +
                "\nSales per customer :\t" + (numberOfSales / numberOfCustomers) +
                "\nPayoff claimed per customer :\t" + (numberOfGivenPayoffs / numberOfCustomers) +
                "\nTotal Income :\t" + (salesVolumes - payoffCumulatedCost) +
                "\n------------------------------------";
    }
}
