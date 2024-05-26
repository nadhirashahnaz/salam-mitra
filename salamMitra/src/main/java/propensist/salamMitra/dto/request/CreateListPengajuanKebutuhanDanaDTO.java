package propensist.salamMitra.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateListPengajuanKebutuhanDanaDTO {
    private List<CreateKebutuhanDanaDTO> listKebutuhanDanaDTO;
    private CreatePengajuanRequestDTO pengajuanDTO;
}
