package mfc.connectors.externaldto.externaldto;

import javax.validation.constraints.NotBlank;

public class NumberplateDTO {

    @NotBlank String numberplate;

    public NumberplateDTO(@NotBlank String numberplate) {
        if (numberplate == null || numberplate.equals(""))
            throw new IllegalArgumentException("Numberplate must not be null or empty");
        this.numberplate = numberplate;
    }
}
