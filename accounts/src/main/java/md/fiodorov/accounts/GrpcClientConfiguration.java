package md.fiodorov.accounts;

import common.grpc.loans.LoansServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GrpcClientConfiguration {

    @Bean
    LoansServiceGrpc.LoansServiceBlockingStub loansServiceStub() {
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        return LoansServiceGrpc.newBlockingStub(channel);
    }
}
