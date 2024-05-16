package md.fiodorov.loansrest.controller.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Borrower(  String name,
        int age,
        double annualIncome) {


}
