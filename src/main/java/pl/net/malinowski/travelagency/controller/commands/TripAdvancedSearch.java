package pl.net.malinowski.travelagency.controller.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class TripAdvancedSearch {
    private String phrase;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;

    @DecimalMin("0.0")
    private BigDecimal priceMin;

    @DecimalMin("0.0")
    private BigDecimal priceMax;
}
