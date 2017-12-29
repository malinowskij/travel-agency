package pl.net.malinowski.travelagency.logic.service.acl;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import java.io.Serializable;

public interface AclManager {
    <T> void addPermission(Class<T> clazz, Long identifier, Sid sid, Permission permission);

    <T> void buildFullPermission(Class<T> clazz, Long identifier, Sid sid);

    <T> void removeFullPermissions(Class<T> clazz, Long identifier, Sid sid);
}
