package com.example.atipergithubapiapplication.model;

import lombok.Data;

@Data
public class BranchInfo {
    private String name;

    private Commit commit;

    @Data
    static public class Commit {
        private String sha;
    }
}

