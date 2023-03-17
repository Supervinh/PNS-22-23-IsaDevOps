package mfc.controllers.dto;

public class DashboardDTO {
    private double numberOfCustomers;
    private double numberOfSales;
    private double numberOfGivenPayoffs;
    private double salesVolumes;
    private double payoffCumulatedCost;

    public DashboardDTO(double numberOfCustomers, double numberOfSales, double numberOfGivenPayoffs, double salesVolumes, double payoffCumulatedCost) {
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfSales = numberOfSales;
        this.numberOfGivenPayoffs = numberOfGivenPayoffs;
        this.salesVolumes = salesVolumes;
        this.payoffCumulatedCost = payoffCumulatedCost;
    }

    public DashboardDTO() {
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
        return "DashboardDTO{" +
                "numberOfCustomers=" + numberOfCustomers +
                ", numberOfSales=" + numberOfSales +
                ", numberOfGivenPayoffs=" + numberOfGivenPayoffs +
                ", salesVolumes=" + salesVolumes +
                ", payoffCumulatedCost=" + payoffCumulatedCost +
                '}';
    }
}