package md.fiodorov.accountsrest.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Borrower(  String name,
        int age,
        double annualIncome) {


}
