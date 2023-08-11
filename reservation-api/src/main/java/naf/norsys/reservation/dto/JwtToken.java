package naf.norsys.reservation.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtToken {

    private String token;

    private Instant createdAt;

    private Instant expiresIn;
}
