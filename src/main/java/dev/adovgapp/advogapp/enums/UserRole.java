package dev.adovgapp.advogapp.enums;

public enum UserRole {
    USER("user"),
    LAWYER("lawyer");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
