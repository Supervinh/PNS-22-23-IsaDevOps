package mfc.controllers.dto;

import mfc.POJO.PayOff;
import java.util.Set;

public class CatalogDTO {

    private Set<PayOff> payoffs;

    public CatalogDTO(Set<PayOff> payoffs) {
        this.payoffs = payoffs;
    }

    public Set<PayOff> getPayoffs() {
        return payoffs;
    }

    public void setPayoffs(Set<PayOff> payoffs) {
        this.payoffs = payoffs;
    }
}
