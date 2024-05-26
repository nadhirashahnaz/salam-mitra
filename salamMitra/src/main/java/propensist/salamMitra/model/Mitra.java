package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("mitra")
@SQLDelete(sql = "UPDATE mitra SET is_deleted = true WHERE id=?")
public class Mitra extends Pengguna {
    @NotNull
    @Column(name = "companyName", nullable = false)
    private String companyName;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "contact", nullable = false)
    private Long contact;

    @OneToMany(mappedBy = "mitra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pengajuan> listPengajuan = new ArrayList<>();
}
