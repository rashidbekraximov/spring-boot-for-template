package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum AuthRole {
    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    USER("User"),
    MANAGER("Manager");

    private final String name;

    AuthRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthRole{" +
                "name='" + name + '\'' +
                '}';
    }
}
