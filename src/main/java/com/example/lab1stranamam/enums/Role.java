package com.example.lab1stranamam.enums;

public enum Role {
    MAN("man"),
    WOMAN("woman"),
    TRADER("trader"),
    ADMIN("admin");

    private final String role;

    Role(String roleName) {
        this.role = roleName;
    }

    public String getRole() {
        return role;
    }
}
