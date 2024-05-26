package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pengguna")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class Pengguna implements Serializable {
    
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "waktu_dibuat", nullable = false)
    @CreationTimestamp
    private Date waktuDibuat;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "pengguna", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notifikasi> listNotifikasi = new ArrayList<>();
    
    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
