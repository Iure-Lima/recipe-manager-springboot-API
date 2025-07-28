package org.recipe.recipebookmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.recipe.recipebookmanager.enums.UserRole;

public record RegisterDTO(
        @NotNull @NotBlank String login,
        @NotNull @NotBlank String password,
        @NotNull @NotBlank String username,
        @NotNull UserRole role
) {}
