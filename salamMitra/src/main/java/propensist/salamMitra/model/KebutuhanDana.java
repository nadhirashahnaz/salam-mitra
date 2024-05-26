package propensist.salamMitra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kebutuhanDana")
public class KebutuhanDana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asnaf", nullable = false)
    private String asnaf;

    @Column(name = "jumlahPenerima", nullable = false)
    private Long jumlahPenerima;

    @Column(name = "pilar", nullable = false)
    private String pilar;

    @Column(name = "jenisBantuan", nullable = false)
    private String jenisBantuan;
    
    @Column(name = "jumlahDana", nullable = false)
    private Long jumlahDana;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pengajuan", referencedColumnName = "id")
    private Pengajuan pengajuan;
}