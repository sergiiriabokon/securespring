package com.example.securespring.model;

import org.springframework.scheduling.config.CronTask;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developers:write");
    
    private final String _permission;

    Permission(String permission) {
        this._permission = permission;
    }

    public String getPermission() {
        return _permission;
    }
}
