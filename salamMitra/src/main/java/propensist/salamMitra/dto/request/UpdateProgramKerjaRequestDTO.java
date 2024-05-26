package propensist.salamMitra.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateProgramKerjaRequestDTO extends CreateProgramKerjaRequestDTO {

    @NotNull
    private Long id;
}
