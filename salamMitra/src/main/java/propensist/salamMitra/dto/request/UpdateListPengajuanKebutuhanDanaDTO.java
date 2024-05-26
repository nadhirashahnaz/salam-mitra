package propensist.salamMitra.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateListPengajuanKebutuhanDanaDTO {
    private List<UpdateKebutuhanDanaDTO> listKebutuhanDanaDTO;
    private UpdatePengajuanRequestDTO pengajuanDTO;
}
