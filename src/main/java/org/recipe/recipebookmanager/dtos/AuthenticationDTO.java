package org.recipe.recipebookmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(
        @NotNull @NotBlank String login,
        @NotBlank @NotNull String password
) {
}
