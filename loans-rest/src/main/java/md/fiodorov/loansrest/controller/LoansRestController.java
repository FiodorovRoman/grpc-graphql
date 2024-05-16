package md.fiodorov.loansrest.controller;


import md.fiodorov.loansrest.controller.model.Loan;
import md.fiodorov.loansrest.controller.model.LoanId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/loans")
public class LoansRestController {
    private final ConcurrentMap<LoanId, Loan> loans = new ConcurrentHashMap<>();

    @PostMapping(consumes = "application/json", produces = "application/json")
    public LoanId createLoan(@RequestBody  Loan loan) {
        LoanId loanId = new LoanId(loan.getGuid());
        loans.put(loanId, loan);
        return loanId;
    }

    @GetMapping(value="/{guid}", produces = "application/json")
    public Loan readLoan(@PathVariable String guid) {
        var id = new LoanId(guid);
        return loans.get(id);

    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public void renegotiateLoan(@RequestBody Loan loan) {
        LoanId loanId = new LoanId(loan.getGuid());
        loans.computeIfPresent(loanId, (k, v) -> loan);
    }

    @DeleteMapping("/{guid}")
    public void deleteLoan(@PathVariable String guid) {
        var id = new LoanId(guid);
        loans.remove(id);

    }
}
