package md.fiodorov.accounts;

import common.grpc.loans.LoansServiceGrpc;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@State(Scope.Benchmark)
public class AccountsApplicationRunner implements ApplicationRunner {


    private final LoansServiceGrpc.LoansServiceBlockingStub stub;
    private final LoanServiceBenchmark loanServiceBenchmark;

    public AccountsApplicationRunner(LoansServiceGrpc.LoansServiceBlockingStub stub, LoanServiceBenchmark loanServiceBenchmark) {
        this.stub = stub;
        this.loanServiceBenchmark = loanServiceBenchmark;
    }


    @Override
    public void run(ApplicationArguments args) {
        loanServiceBenchmark.setup();
        loanServiceBenchmark.run();
    }


}
