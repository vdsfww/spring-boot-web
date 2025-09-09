package com.example.bookshop.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequestDto {

	@NotBlank(message = "Title is required")
	private String title;

	@NotBlank(message = "Author is required")
	private String author;

	@NotBlank(message = "ISBN is required")
	private String isbn;

	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive")
	private BigDecimal price;

	private String description;
	private String coverImage;
}
