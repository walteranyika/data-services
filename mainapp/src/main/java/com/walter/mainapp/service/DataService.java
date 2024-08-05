package com.walter.mainapp.service;

import com.walter.mainapp.clients.ServiceAClient;
import com.walter.mainapp.clients.ServiceBClient;
import com.walter.mainapp.clients.ServiceCClient;
import com.walter.mainapp.dtos.LoanRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class DataService {
    private final ServiceAClient serviceAClient;
    private final ServiceBClient serviceBClient;
    private final ServiceCClient serviceCClient;

    @Autowired
    public DataService(ServiceAClient serviceAClient, ServiceBClient serviceBClient, ServiceCClient serviceCClient) {
        this.serviceAClient = serviceAClient;
        this.serviceBClient = serviceBClient;
        this.serviceCClient = serviceCClient;
    }

    public Page<LoanRequestDto> search(int pageNum, int pageSize, String phone) throws ExecutionException, InterruptedException{
        CompletableFuture<Page<LoanRequestDto>> dataFromA = getDataFromA(0, 100, phone);
        CompletableFuture<Page<LoanRequestDto>> dataFromB = getDataFromB(0, 100, phone);
        CompletableFuture<Page<LoanRequestDto>> dataFromC = getDataFromC(0, 100, phone);

        CompletableFuture.allOf(dataFromA, dataFromB, dataFromC).join();

        long totalNumberElements = dataFromA.get().getTotalElements()+dataFromB.get().getTotalElements()+dataFromC.get().getTotalElements();

        List<LoanRequestDto> aggregatedData = new ArrayList<>();
        aggregatedData.addAll(dataFromA.get().getContent());
        aggregatedData.addAll(dataFromB.get().getContent());
        aggregatedData.addAll(dataFromC.get().getContent());

        var sortedResults = aggregatedData.stream().sorted(Comparator.comparing(LoanRequestDto::getCreatedAt)).toList();

        var paginatedResult = paginate(sortedResults, pageNum, pageSize);

        return new PageImpl<>(paginatedResult, PageRequest.of(pageNum, pageSize), totalNumberElements);
    }

    @Async
    public CompletableFuture<Page<LoanRequestDto>> getDataFromA(int pageNum, int pageSize, String phone ) {
        Page<LoanRequestDto> data = serviceAClient.searchLoanRequests(pageNum, pageSize, phone);
        return CompletableFuture.completedFuture(data);
    }

    @Async
    public CompletableFuture<Page<LoanRequestDto>> getDataFromB(int pageNum, int pageSize, String phone ) {
        Page<LoanRequestDto> data = serviceBClient.searchLoanRequests(pageNum, pageSize, phone);
        return CompletableFuture.completedFuture(data);
    }

    @Async
    public CompletableFuture<Page<LoanRequestDto>> getDataFromC(int pageNum, int pageSize, String phone ) {
        Page<LoanRequestDto> data = serviceCClient.searchLoanRequests(pageNum, pageSize, phone);
        return CompletableFuture.completedFuture(data);
    }

    public <T> List<T> paginate(List<T> list, int page, int size) {
        if (page<1){
            page =1;
        }
        if (size<1){
            size=30;
        }

        int fromIndex = (page - 1) * size;
        if (fromIndex >= list.size()) {
            return new ArrayList<>(); // Return an empty list if the starting index is out of bounds
        }

        int toIndex = Math.min(fromIndex + size, list.size());
        return list.subList(fromIndex, toIndex);
    }
}
