package mfc.controllers.dto;

import java.util.Set;

public class CatalogDTO {

    private Set<PayoffDTO> payoffs;

    public CatalogDTO() {
    }

    public CatalogDTO(Set<PayoffDTO> payoffs) {
        this.payoffs = payoffs;
    }

    public Set<PayoffDTO> getPayoffs() {
        return payoffs;
    }

    public void setPayoffs(Set<PayoffDTO> payoffs) {
        this.payoffs = payoffs;
    }

    @Override
    public String toString() {
        return "CatalogDTO{" +
                "payoffs=" + payoffs +
                '}';
    }
}
