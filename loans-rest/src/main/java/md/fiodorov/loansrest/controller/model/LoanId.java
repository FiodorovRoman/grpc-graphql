package md.fiodorov.loansrest.controller.model;

import lombok.Builder;

@Builder
public record LoanId(String guid) {
    @Override
    public String guid() {
        return guid;
    }
}
