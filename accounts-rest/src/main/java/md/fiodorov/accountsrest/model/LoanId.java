package md.fiodorov.accountsrest.model;

import lombok.Builder;

@Builder
public record LoanId(String guid) {
    @Override
    public String guid() {
        return guid;
    }
}
