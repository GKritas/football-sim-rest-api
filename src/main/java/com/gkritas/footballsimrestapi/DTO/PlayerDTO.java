package com.gkritas.footballsimrestapi.DTO;

import com.gkritas.footballsimrestapi.model.Player;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String position;
    private Long teamId;
}
