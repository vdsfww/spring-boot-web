package org.example.bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.bookshop.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password",
        second = "confirmPassword",
        message = "Passwords must match")
public class UserRegistrationRequestDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 35)
    private String password;

    @NotBlank
    @Length(min = 8, max = 35)
    private String confirmPassword;

    private String shippingAddress;

}
