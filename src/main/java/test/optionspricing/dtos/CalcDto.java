package test.optionspricing.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalcDto {

    private Double interest;
    private Double price;
    private int steps;
    private boolean isEuropean;
    private boolean isPut;
    private int daysToExpiry;
    private Double sigma;

}
