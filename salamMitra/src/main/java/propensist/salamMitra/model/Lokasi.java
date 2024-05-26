package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lokasi")
public class Lokasi  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provinsi")
    private String provinsi;

    @Column(name = "kabupatenKota")
    private String kabupatenKota;

    @Column(name = "kecamatan")
    private String kecamatan;
}
