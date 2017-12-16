package pl.net.malinowski.travelagency.data.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public enum Type {
        ROLE_CUSTOMER,
        ROLE_ADMIN
    }
}
