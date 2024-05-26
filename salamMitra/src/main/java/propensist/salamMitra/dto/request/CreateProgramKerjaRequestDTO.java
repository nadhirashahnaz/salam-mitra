package propensist.salamMitra.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateProgramKerjaRequestDTO {

    @Size(max = 50, message = "Judul program tidak boleh lebih dari 50 karakter!")
    private String judul;

    private String kategoriProgram;

    @NotNull(message = "Kategori Asnaf tidak boleh kosong!")
    private List<String> kategoriAsnaf;

    private String deskripsi;

    @NotNull(message = "Provinsi tidak boleh kosong!")
    private List<String> provinsi;  

    private List<String> kabupatenKota;  

    private byte[] fotoProgram;
}
