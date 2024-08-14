package com.example.atipergithubapiapplication.service.impl;

import com.example.atipergithubapiapplication.exception.RepositoryFetchException;
import com.example.atipergithubapiapplication.exception.UserNotFoundException;
import com.example.atipergithubapiapplication.model.BranchInfo;
import com.example.atipergithubapiapplication.model.RepositoryInfo;
import com.example.atipergithubapiapplication.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepositoryServiceImpl implements RepositoryService {
    private RestTemplate restTemplate;

    public RepositoryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RepositoryInfo> getRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        try {
            ResponseEntity<RepositoryInfo[]> repositoriesResponce =
                    restTemplate.getForEntity(url, RepositoryInfo[].class);
            return Arrays.stream(repositoriesResponce.getBody())
                    .filter(rep -> !rep.isFork())
                    .map(this::populateBranches)
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User " + username + " not found");
            } else {
                throw new RepositoryFetchException(
                        "An error occurred while fetching repositories " + username + " user");
            }
        }

    }

    private RepositoryInfo populateBranches(RepositoryInfo repository) {
        repository.setBranches(getBranches(repository.getOwner().getLogin(), repository.getName()));
        return repository;
    }

    private List<BranchInfo> getBranches(String ownerLogin, String repoName) {
        String url = "https://api.github.com/repos/" + ownerLogin + "/" + repoName + "/branches";
        ResponseEntity<BranchInfo[]> response = restTemplate.getForEntity(url, BranchInfo[].class);
        return Arrays.asList(response.getBody());
    }

}
