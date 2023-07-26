package naf.norsys.reservation.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends GenericDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @NotNull
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private LocalDate birthDay;

    private String profilePicture;

    private String verificationCode;

    private boolean profileCompleted;

    private String bio;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;


}
