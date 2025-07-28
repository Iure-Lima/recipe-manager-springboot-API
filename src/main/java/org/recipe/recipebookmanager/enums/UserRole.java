package org.recipe.recipebookmanager.enums;

import lombok.Getter;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    @Getter
    private String role;

    UserRole(String role){
        this.role = role;
    }
}
