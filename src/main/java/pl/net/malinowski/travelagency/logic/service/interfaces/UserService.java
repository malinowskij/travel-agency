package pl.net.malinowski.travelagency.logic.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.net.malinowski.travelagency.controller.commands.EditUserForm;
import pl.net.malinowski.travelagency.controller.commands.PhraseSearch;
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
    boolean checkEmailAvailability(String email);

    Page<User> findAllPaginated(Pageable pageable);

    Page<User> findByPhrasePaginated(PhraseSearch phraseSearch, Pageable pageable);

    boolean isAdminLogged();
}
