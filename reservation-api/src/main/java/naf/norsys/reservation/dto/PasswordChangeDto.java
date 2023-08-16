package naf.norsys.reservation.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeDto {
    private String currentPassword;
    private String newPassword;
}
