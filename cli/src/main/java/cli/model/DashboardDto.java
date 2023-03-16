package cli.model;

public class DashboardDto {
    private double numberOfCustomers;
    private double numberOfSales;
    private double salesVolumes;
    private double payoffCumulatedPayoff;

    public DashboardDto(double numberOfCustomers, double numberOfSales, double salesVolumes, double payoffCumulatedPayoff) {
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfSales = numberOfSales;
        this.salesVolumes = salesVolumes;
        this.payoffCumulatedPayoff = payoffCumulatedPayoff;
    }

    public DashboardDto() {
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

    public double getPayoffCumulatedPayoff() {
        return payoffCumulatedPayoff;
    }

    public void setPayoffCumulatedPayoff(double payoffCumulatedPayoff) {
        this.payoffCumulatedPayoff = payoffCumulatedPayoff;
    }
}
