package com.walter.ServiceB.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walter.ServiceB.services.LoanRequestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.walter.ServiceB.models.LoanRequest;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final LoanRequestService loanRequestService;


    public DataLoader(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper= new ObjectMapper();
        TypeReference<List<LoanRequest>> typeReference = new TypeReference<List<LoanRequest>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/requests.json");
        try {
            List<LoanRequest> requests = mapper.readValue(inputStream, typeReference);
            requests.forEach(loanRequestService::insert);
            System.out.println("Request Saved");
        }catch (Exception e){
            System.out.println("Unable to save request with error : "+ e.getMessage());
        }
    }
}
