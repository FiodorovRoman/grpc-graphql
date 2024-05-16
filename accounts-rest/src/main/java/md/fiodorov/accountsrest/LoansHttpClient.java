package md.fiodorov.accountsrest;

import com.fasterxml.jackson.databind.ObjectMapper;

import md.fiodorov.accountsrest.model.Loan;
import md.fiodorov.accountsrest.model.LoanId;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;

@State(Scope.Thread)
public class LoansHttpClient {

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public LoansHttpClient() {
        httpClient = HttpClients.custom()
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public LoanId createLoan(Loan loan) throws IOException, ParseException {
        HttpPost request = new HttpPost("http://localhost:8080/loans");
        String json = objectMapper.writeValueAsString(loan);
        request.setEntity(new StringEntity(json));
        request.setHeader("Content-Type", "application/json");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(responseBody, LoanId.class);
        }
    }

    public Loan readLoan(LoanId loanId) throws IOException, ParseException {
        HttpGet request = new HttpGet("http://localhost:8080/loans/" + loanId.guid());
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(responseBody, Loan.class);
        }
    }

    public void renegotiateLoan(Loan loan) throws IOException, ParseException {
        HttpPut request = new HttpPut("http://localhost:8080/loans");
        String json = objectMapper.writeValueAsString(loan);
        request.setEntity(new StringEntity(json));
        request.setHeader("Content-Type", "application/json");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    public void deleteLoan(LoanId loanId) throws IOException, ParseException {
        HttpDelete request = new HttpDelete("http://localhost:8080/loans/" + loanId.guid());
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    public void close() throws IOException {
        httpClient.close();
    }

}