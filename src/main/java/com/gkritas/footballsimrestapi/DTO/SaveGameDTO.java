package com.gkritas.footballsimrestapi.DTO;

import com.gkritas.footballsimrestapi.model.SaveGame;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveGameDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<LeagueDTO> leagues;
}
