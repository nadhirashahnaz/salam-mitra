package propensist.salamMitra.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import propensist.salamMitra.model.Admin;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateAdminRequestDTO {
    
    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String role;

    @NotNull
    private String fullName;

    @NotNull
    private String address;

    @NotNull
    private String gender;

    @NotNull
    private Long contact;

    @NotNull
    private Admin.AdminRole adminRole;

}
