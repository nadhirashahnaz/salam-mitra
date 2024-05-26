package propensist.salamMitra.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pencairan")
public class Pencairan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "source_of_found", nullable = false)
    private String sourceOfFound;

    @NotNull
    @Column(name = "channeling", nullable = false)
    private String channeling;

    @NotNull
    @Column(name = "jenis_dana", nullable = false)
    private String jenisDana;

    @NotNull
    @Column(name = "link_pencairan", nullable = false)
    private String linkPencairan;

    @Column(name = "tanggal_pencairan_salam_setara", nullable = false)
    @CreationTimestamp
    private Date tanggalPencairanSalamSetara;
    
    @Column(name = "tanggal_pencairan_mitra")
    private Date tanggalPencairanMitra;

    @Lob
    @Column(name = "bukti_pencairan_mitra")
    private byte[] buktiPencairanMitra;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pengajuan", referencedColumnName = "id")
    private Pengajuan pengajuan;

}
