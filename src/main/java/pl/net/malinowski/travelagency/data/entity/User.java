package pl.net.malinowski.travelagency.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pl.net.malinowski.travelagency.controller.validator.annotations.EmailUnique;
import pl.net.malinowski.travelagency.controller.validator.annotations.PasswordEquals;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
//@PasswordEquals
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "{user.firstname.notempty}")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @NotEmpty(message = "{user.lastname.notempty}")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @EmailUnique
    @NotNull
    @NotEmpty(message = "{user.email.notempty}")
    @Email
    @Column(unique = true, nullable = false, length = 64)
    private String email;

    @NotNull
    @NotEmpty(message = "{user.password.notempty}")
    @Column(nullable = false, length = 64)
    private String password;

    @NotNull
    @NotEmpty
    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotNull
    @Column(name = "tel_number")
    private String telNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

}
