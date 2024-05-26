package propensist.salamMitra.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdatePengajuanRequestDTO extends CreatePengajuanRequestDTO {
    private Long id;
}
