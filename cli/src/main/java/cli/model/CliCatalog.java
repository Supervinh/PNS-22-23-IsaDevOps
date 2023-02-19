package cli.model;

import java.util.Set;

public class CliCatalog {
    private Set<CliPayoff> payoffs;

    public CliCatalog() {
    }

    public CliCatalog(Set<CliPayoff> payoffs) {
        this.payoffs = payoffs;
    }

    public Set<CliPayoff> getPayoffs() {
        return payoffs;
    }

    public void setPayoffs(Set<CliPayoff> payoffs) {
        this.payoffs = payoffs;
    }

    @Override
    public String toString() {
        return "CliCatalog{" +
                "payoffs=" + payoffs +
                '}';
    }
}
