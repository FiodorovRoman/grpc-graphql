package md.fiodorov.accountsrest;

import common.grpc.loans.LoansServiceGrpc;
import org.apache.hc.core5.http.ParseException;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@State(Scope.Benchmark)
public class AccountsApplicationRunner implements ApplicationRunner {

    private final LoansServiceBenchmark loanServiceBenchmark;

    public AccountsApplicationRunner(LoansServiceBenchmark loanServiceBenchmark) {
        this.loanServiceBenchmark = loanServiceBenchmark;
    }


    @Override
    public void run(ApplicationArguments args) throws IOException, ParseException {
        loanServiceBenchmark.run();
    }

}
