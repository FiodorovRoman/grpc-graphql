package md.fiodorov.loans;

import com.google.protobuf.Empty;
import common.grpc.loans.Loan;
import common.grpc.loans.LoanId;
import common.grpc.loans.LoansServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@GRpcService
public class LoansServiceImpl extends LoansServiceGrpc.LoansServiceImplBase {

    private final ConcurrentMap<LoanId, Loan> loans = new ConcurrentHashMap<>();

    @Override
    public void createLoan(Loan loan, StreamObserver<LoanId> responseObserver) {
        LoanId loanId = LoanId.newBuilder().setGuid(loan.getGuid()).build();
        loans.put(loanId, loan);

        responseObserver.onNext(loanId);
        responseObserver.onCompleted();
    }

    @Override
    public void readLoan(LoanId loanId, StreamObserver<Loan> responseObserver) {
        Loan loan = loans.get(loanId);

        responseObserver.onNext(loan);
        responseObserver.onCompleted();
    }

    @Override
    public void renegotiateLoan(Loan loan, StreamObserver<Empty> responseObserver) {
        LoanId loanId = LoanId.newBuilder().setGuid(loan.getGuid()).build();
        loans.computeIfPresent(loanId, (k, v) -> loan);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteLoan(LoanId loanId, StreamObserver<Empty> responseObserver) {
        loans.remove(loanId);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
