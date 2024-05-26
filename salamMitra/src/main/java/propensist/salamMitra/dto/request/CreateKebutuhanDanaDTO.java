package propensist.salamMitra.dto.request;

import propensist.salamMitra.model.Pengajuan;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateKebutuhanDanaDTO {
    private Long idPengajuan;
    private Pengajuan pengajuan;
    private String asnaf;
    private String pilar;
    private Long jumlahDana;
    private String jenisBantuan;
    private Long jumlahPenerima;
    
}
