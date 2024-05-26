package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("manajemen")
@SQLDelete(sql = "UPDATE manajemen SET is_deleted = true WHERE id=?")
public class Manajemen extends Pengguna {
    @NotNull
    @Column(name = "fullName", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Column(name = "contact", nullable = false)
    private Long contact;
}
