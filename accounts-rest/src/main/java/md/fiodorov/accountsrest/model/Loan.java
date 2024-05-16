package md.fiodorov.accountsrest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class Loan {
    private String guid;
    private double requestedAmount;
    private int termMonths;
    private float annualInterest;
    private Borrower borrower;
    private String longDescription;
}
