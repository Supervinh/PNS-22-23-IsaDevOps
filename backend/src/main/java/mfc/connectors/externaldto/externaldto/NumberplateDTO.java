package mfc.connectors.externaldto.externaldto;

public class NumberplateDTO {

    String numberplate;

    public NumberplateDTO(String numberplate) {
        if (numberplate == null || numberplate.equals(""))
            throw new IllegalArgumentException("Numberplate must not be null or empty");
        this.numberplate = numberplate;
    }

    public NumberplateDTO() {
    }

    public String getNumberplate() {
        return numberplate;
    }

    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }
}
