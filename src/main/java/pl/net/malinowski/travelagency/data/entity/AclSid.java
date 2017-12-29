package pl.net.malinowski.travelagency.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "acl_sid")
@Getter @Setter @NoArgsConstructor
public class AclSid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean principal;

    private String sid;

    public AclSid(boolean principal, String sid) {
        this.principal = principal;
        this.sid = sid;
    }
}
