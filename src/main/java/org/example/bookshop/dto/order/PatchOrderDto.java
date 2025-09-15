package org.example.bookshop.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.bookshop.model.Status;

@Getter
@Setter
public class PatchOrderDto {
    @NotNull
    private Status status;
}
