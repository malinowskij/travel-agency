package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.controller.commands.EditUserForm;
import pl.net.malinowski.travelagency.data.entity.Address;
import pl.net.malinowski.travelagency.data.entity.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User save(User user);
    List<User> findAll();
    User getLoggedInUser();
    User update(User user);
    void updateAddress(Long addressId, Long userId);
    EditUserForm mapUserToEditUserForm(User user);
    User mapEditUserFormToUser(EditUserForm form);
}
