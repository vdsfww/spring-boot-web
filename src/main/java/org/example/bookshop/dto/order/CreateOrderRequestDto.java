package org.example.bookshop.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateOrderRequestDto {
    @NotBlank
    @Length(max = 500)
    @Schema(
            description = "Order address",
            example = "Kyiv, Shevchenko ave, 1",
            maxLength = 500,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String shippingAddress;
}
