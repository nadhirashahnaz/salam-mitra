package propensist.salamMitra.model;

import java.util.Date;

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
@Table(name = "notifikasi")
public class Notifikasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Column(name = "notified_date", nullable = false)
    private Date notifiedDate;

    @NotNull
    @Column(name = "read", nullable = false)
    private Boolean read = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pengguna", referencedColumnName = "id")
    private Pengguna pengguna;

    @NotNull
    @Column(name = "id_pengajuan", nullable = false)
    private Long idPengajuan;
    
}
