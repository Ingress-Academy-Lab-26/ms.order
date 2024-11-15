package az.ingress.ms_order.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import static az.ingress.ms_order.model.enums.DateTimeConstants.DATE_PATTERN;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCriteria {
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate createdAtFrom;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate createdAtTo;
}
