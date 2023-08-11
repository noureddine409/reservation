package naf.norsys.reservation.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
