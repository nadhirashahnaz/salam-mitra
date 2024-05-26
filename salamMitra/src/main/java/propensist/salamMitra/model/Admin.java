package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("admin")
@SQLDelete(sql = "UPDATE admin SET is_deleted = true WHERE id=?")
public class Admin extends Pengguna {
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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "adminRole", nullable = false)
    private AdminRole adminRole;

    public enum AdminRole {
        FINANCE, PROGRAM;
    
        public static AdminRole fromString(String value) {
            for (AdminRole role : AdminRole.values()) {
                if (Objects.equals(role.name(), value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid AdminRole: " + value);
        }
    }
}
