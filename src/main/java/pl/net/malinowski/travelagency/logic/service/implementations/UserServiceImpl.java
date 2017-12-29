package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.EditUserForm;
import pl.net.malinowski.travelagency.controller.commands.PhraseSearch;
import pl.net.malinowski.travelagency.data.entity.AclSid;
import pl.net.malinowski.travelagency.data.entity.Role;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.data.repository.SidRepository;
import pl.net.malinowski.travelagency.data.repository.UserRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.RoleService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private final SidRepository sidRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleService roleService, SidRepository sidRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.sidRepository = sidRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        user.setRoles(new HashSet<>(Arrays.asList(roleService.findByName(Role.Type.ROLE_USER))));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getId() == null)
            sidRepository.save(new AclSid(true, user.getEmail()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(authentication.getName());
    }

    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void updateAddress(Long addressId, Long userId) {
        userRepository.updateAddress(addressId, userId);
    }

    @Override
    public EditUserForm mapUserToEditUserForm(User user) {
        return new EditUserForm(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPassword(), null, user.getBirthDate(), user.getTelNumber(), user.getAddress(), user.getRoles());
    }

    @Override
    public User mapEditUserFormToUser(EditUserForm form) {
        return new User(form.getId(), form.getFirstName(), form.getLastName(), form.getEmail(),
                form.getPassword(), form.getConfirmPassword(), form.getBirthDate(), form.getTelNumber(), form.getAddress(), form.getRoles());
    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return userRepository.countUserWithEmail(email, getLoggedInUser().getEmail()) == 0;
    }

    @Override
    public Page<User> findAllPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findByPhrasePaginated(PhraseSearch phraseSearch, Pageable pageable) {
        return userRepository.findByPhraseSearch("%" + phraseSearch.getPhrase() + "%", pageable);
    }

    @Override
    public boolean isAdminLogged() {
        return this.getLoggedInUser().getRoles()
                .stream().anyMatch(r -> r.getName() == (Role.Type.ROLE_ADMIN));
    }
}
