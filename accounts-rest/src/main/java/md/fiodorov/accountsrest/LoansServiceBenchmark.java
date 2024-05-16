package md.fiodorov.accountsrest;


import md.fiodorov.accountsrest.model.Borrower;
import md.fiodorov.accountsrest.model.Loan;
import md.fiodorov.accountsrest.model.LoanId;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.ParseException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@State(Scope.Thread)
public class LoansServiceBenchmark {

    private LoansHttpClient loansHttpClient;

    @Setup(Level.Trial)
    public void setup() {
        this.loansHttpClient = new LoansHttpClient();
    }

    @Benchmark
    @Fork(value = 1, warmups = 2)
    @BenchmarkMode(Mode.All)
    public void run() throws IOException, ParseException {
        // create
        var borrowerBuilder = new Borrower("John Doe", 34, 100_000);

        var loan = new Loan(UUID.randomUUID().toString(), 1_000_000_000, 12, 6.5f, borrowerBuilder, StringUtils.repeat("I will never use gRPC --- ", 1_000));


        var id = create(loan);

        readLoan(id);

        // update
        loan.setTermMonths(24);
        loan.setAnnualInterest(7.5f);
        loan.setLongDescription("gRPC is really cool ---");

        update(loan, id);
    }

    private LoanId create(Loan loan) throws IOException, ParseException {

        LoanId id = loansHttpClient.createLoan(loan);
        System.out.println("** Loan created with " + id);
        return id;
    }

    private void readLoan(LoanId id) throws IOException, ParseException {
        // read
        System.out.println("** Loan read:\n" + loansHttpClient.readLoan(id));
    }

    private void update(Loan loanUpdated, LoanId loanId) throws IOException, ParseException {

        loansHttpClient.renegotiateLoan(loanUpdated);
        System.out.println("** Loan updated:\n" + loansHttpClient.readLoan(loanId));
    }

    private void delete(LoanId id) throws IOException, ParseException {
        // delete
        loansHttpClient.deleteLoan(id);
        System.out.println("** Is Loan deleted: " +
                loansHttpClient.readLoan(id).getGuid().isBlank());
    }
}
