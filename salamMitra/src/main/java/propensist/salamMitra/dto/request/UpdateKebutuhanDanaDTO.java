package propensist.salamMitra.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateKebutuhanDanaDTO extends CreateKebutuhanDanaDTO {
    private Long id;
}
