package naf.norsys.reservation.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
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
    @Builder
    public UserDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String firstName, String lastName, String email, String password, LocalDate birthDay, String profilePicture, String verificationCode, boolean profileCompleted, String bio, boolean enabled) {
        super(id, createdAt, updatedAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDay = birthDay;
        this.profilePicture = profilePicture;
        this.verificationCode = verificationCode;
        this.profileCompleted = profileCompleted;
        this.bio = bio;
        this.enabled = enabled;
    }
}
