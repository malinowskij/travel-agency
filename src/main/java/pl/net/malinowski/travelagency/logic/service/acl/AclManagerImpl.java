package pl.net.malinowski.travelagency.logic.service.acl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.net.malinowski.travelagency.data.entity.Booking;

import java.io.Serializable;
import java.util.Arrays;

@Service
public class AclManagerImpl implements AclManager {

    private static final Logger log = LoggerFactory.getLogger(AclManagerImpl.class);

    private final MutableAclService aclService;

    @Autowired
    public AclManagerImpl(MutableAclService aclService) {
        this.aclService = aclService;
    }

    @Override
    public <T> void addPermission(Class<T> clazz, Long identifier, Sid sid, Permission permission) {
        ObjectIdentity oi = new ObjectIdentityImpl(clazz, identifier);
        MutableAcl acl = findAcl(oi);

        try {
            log.info("INSERT ACL (PERMISSION) to " + clazz.getSimpleName() + " id = " + identifier);
            acl.insertAce(acl.getEntries().size(), permission, sid, true);
            aclService.updateAcl(acl);
        } catch (RuntimeException ignored) {
            //PSQLException
        }
    }

    private MutableAcl findAcl(ObjectIdentity oi) {
        try {
            return (MutableAcl) aclService.readAclById(oi);
        } catch (NotFoundException ex) {
            return aclService.createAcl(oi);
        }
    }

    @Override
    public <T> void buildFullPermission(Class<T> clazz, Long identifier, Sid sid) {
        ObjectIdentity oi = new ObjectIdentityImpl(clazz, identifier);
        MutableAcl acl = findAcl(oi);


        try {
            log.info("INSERT ACL (FULL PERMISSION) to " + clazz.getSimpleName() + " id = " + identifier);
            acl.insertAce(0, BasePermission.ADMINISTRATION, sid, true);
            acl.insertAce(1, BasePermission.DELETE, sid, true);
            acl.insertAce(2, BasePermission.WRITE, sid, true);
            acl.insertAce(3, BasePermission.READ, sid, true);
            acl.insertAce(4, BasePermission.CREATE, sid, true);
            aclService.updateAcl(acl);
        } catch (RuntimeException ex) {
            //PSQLException
            log.debug(ex.getMessage());
        }

    }

    @Override
    public <T> void removeFullPermissions(Class<T> clazz, Long identifier, Sid sid) {
        ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier.toString());
        aclService.deleteAcl(identity, true);
        log.info("DELETE ACL (FULL PERMISSION) to " + clazz.getSimpleName() + " id = " + identifier);
    }
}
