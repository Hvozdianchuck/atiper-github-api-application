package com.example.atipergithubapiapplication.service;

import com.example.atipergithubapiapplication.model.RepositoryInfo;

import java.util.List;

public interface RepositoryService {
    List<RepositoryInfo> getRepositories(String username);
}
