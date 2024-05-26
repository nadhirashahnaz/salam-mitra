package propensist.salamMitra.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateLokasiRequestDTO {
    private String provinsi;

    private String kabupatenKota;

    private String kecamatan;

}
