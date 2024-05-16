package md.fiodorov.accounts;

import common.grpc.loans.Borrower;
import common.grpc.loans.Loan;
import common.grpc.loans.LoanId;
import common.grpc.loans.LoansServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@State(Scope.Thread)
public class LoanServiceBenchmark {


    private LoansServiceGrpc.LoansServiceBlockingStub stub;

    @Setup(Level.Trial)
    public void setup() {
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.stub = LoansServiceGrpc.newBlockingStub(channel);
    }



    @Benchmark
    @Fork(value = 1, warmups = 2)
    @BenchmarkMode(Mode.All)
    public void run(){
        // create
        var borrowerBuilder = Borrower.newBuilder()
                .setName("John Doe")
                .setAge(34)
                .setAnnualIncome(100_000);

        var loanBuilder = Loan.newBuilder()
                .setGuid(UUID.randomUUID().toString())
                .setBorrower(borrowerBuilder.build())
                .setRequestedAmount(1_000_000_000)
                .setTermMonths(12)
                .setAnnualInterest(6.5f)
                .setLongDescription(StringUtils.repeat("I will never use gRPC --- ", 1_000));


        var id = create(loanBuilder);

        readLoan(id);

        // update
        Loan loanUpdated = loanBuilder
                .setTermMonths(24)
                .setAnnualInterest(7.5f)
                .setLongDescription("gRPC is really cool ---")
                .build();

        update(loanUpdated, id);
    }

    private LoanId create(Loan.Builder loanBuilder){

        LoanId id = stub.createLoan(loanBuilder.build());
        System.out.println("** Loan created with " + id);
        return id;
    }

    private void readLoan(LoanId id) {
        // read
        System.out.println("** Loan read:\n" + stub.readLoan(id));
    }

    private void update(Loan loanUpdated, LoanId loanId) {

        stub.renegotiateLoan(loanUpdated);
        System.out.println("** Loan updated:\n" + stub.readLoan(loanId));
    }

    private void delete(LoanId id) {
        // delete
        stub.deleteLoan(id);
        System.out.println("** Is Loan deleted: " +
                stub.readLoan(id).getGuid().isBlank());
    }
}
