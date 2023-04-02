package domain;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
//import validator.ValidEmail;

@Getter
public class Account {

    @NotNull
    @Min(10000)
    @NumberFormat(pattern = "#,##0.00")
    @Setter
    private BigDecimal balance = new BigDecimal("20003000.2599");

    // @DecimalMin("0.0")
    @DecimalMin(value = "0.0", message = "must be greater than or equal to 0%")
    @DecimalMax(value = "0.6", message = "must be less than or equal to 60%")
    @NumberFormat(style = Style.PERCENT)
    @Setter
    private double percent = 0.25;

    private BigDecimal balance2;
    private double percent2;

    @NotBlank
    @Email
    // @ValidEmail
    @Setter
    private String email;

    public void simpleExample() {
	balance2 = new BigDecimal("20003000.2599");
	percent2 = percent;
    }
}
