package com.example.atipergithubapiapplication.model;

import lombok.Data;

import java.util.List;

@Data
public class RepositoryInfo {
    private String name;

    private OwnerInfo owner;

    private boolean fork;

    private List<BranchInfo> branches;

    @Data
    public static class OwnerInfo {
        private String login;
    }
}
