package md.fiodorov.loansrest.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Loan {
    private String guid;
    private double requestedAmount;
    private int termMonths;
    private float annualInterest;
    private Borrower borrower;
    private String longDescription;
}
