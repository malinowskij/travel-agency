package pl.net.malinowski.travelagency.logic.service.acl;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import java.io.Serializable;

public interface AclManager {
    <T> void addPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);

    <T> void removePermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);

    <T> boolean isPermissionGranted(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);

}
