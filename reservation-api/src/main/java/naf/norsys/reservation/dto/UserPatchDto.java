package naf.norsys.reservation.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPatchDto {

    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private String bio;


}
