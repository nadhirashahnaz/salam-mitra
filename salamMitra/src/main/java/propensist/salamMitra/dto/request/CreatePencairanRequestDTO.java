package propensist.salamMitra.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import propensist.salamMitra.model.Pengajuan;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CreatePencairanRequestDTO {

    private String sourceOfFound;

    private String channeling;

    private String jenisDana;

    private String linkPencairan;

    private Pengajuan pengajuan;

}
