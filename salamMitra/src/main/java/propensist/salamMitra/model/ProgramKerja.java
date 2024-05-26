package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "program_kerja")
public class ProgramKerja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "judul", nullable = false)
    private String judul;

    @NotNull
    @Column(name = "kategori_program", nullable = false)
    private String kategoriProgram;

    @ElementCollection
    @CollectionTable(name = "kategori_asnaf", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "kategori_asnaf")
    private List<String> kategoriAsnaf;

    @Lob
    @NotNull
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @ElementCollection
    @CollectionTable(name = "provinsi", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "provinsi")
    private List<String> provinsi;

    @ElementCollection
    @CollectionTable(name = "kabupaten_kota", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "kabupaten_kota")
    private List<String> kabupatenKota;

    @Lob
    @Column(name = "foto_program")
    private byte[] fotoProgram;

    @Transient
    private String imageBase64;

    @NotNull
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted = false;
}
