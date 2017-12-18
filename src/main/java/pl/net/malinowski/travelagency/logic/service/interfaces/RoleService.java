package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findByName(Role.Type type);
}
