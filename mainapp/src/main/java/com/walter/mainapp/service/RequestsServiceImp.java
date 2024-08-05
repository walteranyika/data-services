package com.walter.mainapp.service;

import com.walter.mainapp.clients.MainClient;
import com.walter.mainapp.clients.ServiceAClient;
import com.walter.mainapp.clients.ServiceBClient;
import com.walter.mainapp.clients.ServiceCClient;
import com.walter.mainapp.dtos.LoanRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RequestsServiceImp  implements RequestsService{

    private final ServiceAClient serviceAClient;
    private final ServiceBClient serviceBClient;
    private final ServiceCClient serviceCClient;

    @Autowired
    public RequestsServiceImp(ServiceAClient serviceAClient, ServiceBClient serviceBClient, ServiceCClient serviceCClient) {
        this.serviceAClient = serviceAClient;
        this.serviceBClient = serviceBClient;
        this.serviceCClient = serviceCClient;
    }

    @Override
    public Page<LoanRequestDto> search(int pageNum, int pageSize, String phone) {
        List<LoanRequestDto> aggregatedList = new ArrayList<>();


        Page<LoanRequestDto> requestsPageA = serviceAClient.searchLoanRequests(0, pageSize, phone);
        Page<LoanRequestDto> requestsPageB = serviceBClient.searchLoanRequests(0, pageSize, phone);
        Page<LoanRequestDto> requestsPageC = serviceCClient.searchLoanRequests(0, pageSize, phone);

        // Get total elements from each service
        long totalElementsA = requestsPageA.getTotalElements();
        long totalElementsB = requestsPageB.getTotalElements();
        long totalElementsC = requestsPageC.getTotalElements();

        // Calculate total elements
        long totalElements = totalElementsA + totalElementsB + totalElementsC;

        // Calculate proportions
        double proportionA = (double) totalElementsA / totalElements;
        double proportionB = (double) totalElementsB / totalElements;
        double proportionC = (double) totalElementsC / totalElements;

        // Calculate the number of elements to fetch from each service for the requested size
        int sizeA = (int) Math.round(pageSize * proportionA);
        int sizeB = (int) Math.round(pageSize * proportionB);
        int sizeC = (int) Math.round(pageSize * proportionC);

        // Calculate the offset for the requested page
        int offset = (pageNum - 1) * pageSize;


        // Fetch the appropriate number of items starting from the offset
        log.info("Page Sizes A {}, B {}, C {}", sizeA, sizeB, sizeC);
        List<LoanRequestDto> usersFromA = fetchRequestsFromService(serviceAClient, phone, pageNum*sizeA, sizeA);
        List<LoanRequestDto> usersFromB = fetchRequestsFromService(serviceBClient, phone, pageNum*sizeB, sizeB);
        List<LoanRequestDto> usersFromC = fetchRequestsFromService(serviceCClient, phone, pageNum*sizeC, sizeC);

        // Combine the results into a single list
        aggregatedList.addAll(usersFromA);
        aggregatedList.addAll(usersFromB);
        aggregatedList.addAll(usersFromC);

        // Ensure the list size is exactly the requested size
        var results = adjustListSize(aggregatedList, pageSize);
        return new PageImpl<>(results, PageRequest.of(pageNum, pageSize), totalElements);
    }

    private List<LoanRequestDto> fetchRequestsFromService(MainClient serviceClient, String phone, int offset, int size) {
        List<LoanRequestDto> resultList = new ArrayList<>();
        log.info("Offset {} and size {}", offset, size);
        int currentPage = offset / size;
        int remainingSize = size;
        while (resultList.size() < size) {
            Page<LoanRequestDto> pageResult = serviceClient.searchLoanRequests(currentPage, remainingSize, phone);
            resultList.addAll(pageResult.getContent());
            if (!pageResult.hasNext()) {
                break;
            }
            currentPage++;
            remainingSize = size - resultList.size();
        }
        return resultList;
    }

    private List<LoanRequestDto> adjustListSize(List<LoanRequestDto> list, int size) {
        if (list.size() > size) {
            return list.subList(0, size);
        }
        return list;
    }
}
