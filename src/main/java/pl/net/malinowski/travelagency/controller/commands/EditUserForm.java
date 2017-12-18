package pl.net.malinowski.travelagency.controller.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pl.net.malinowski.travelagency.data.entity.Address;
import pl.net.malinowski.travelagency.data.entity.Role;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class EditUserForm {
    private Long id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String confirmPassword;

    @Past
    @NotNull
    private Date birthDate;

    @NotNull
    @NotEmpty
    private String telNumber;

    @Valid
    private Address address;

    private Set<Role> roles;

    public EditUserForm(Long id, String firstName, String lastName, String email, String password,
                        String confirmPassword, Date birthDate, String telNumber, Address address, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.birthDate = birthDate;
        this.telNumber = telNumber;
        this.address = address;
        this.roles = roles;
    }
}
