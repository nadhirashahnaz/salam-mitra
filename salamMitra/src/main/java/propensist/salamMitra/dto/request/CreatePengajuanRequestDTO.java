package propensist.salamMitra.dto.request;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import propensist.salamMitra.model.KebutuhanDana;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePengajuanRequestDTO {
    private String namaMitra;

    private String namaProgram;

    private String kategori;

    private String namaPIC;

    private String kontakPIC;

    private String bank;

    private String namaPemilikRekening;

    private Long nomorRekening;

    private String alamatKantor;

    private List<KebutuhanDana> listKebutuhanDana = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the format here
    private Date tanggalMulai;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the format here
    private Date tanggalSelesai;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the format here
    private Date tanggalLaporan;

    private String provinsi;

    private String kabupatenKota;

    private String kecamatan;

    private String alamatLengkap;
    
}
