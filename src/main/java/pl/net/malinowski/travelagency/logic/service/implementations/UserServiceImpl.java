package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.EditUserForm;
import pl.net.malinowski.travelagency.data.entity.Address;
import pl.net.malinowski.travelagency.data.entity.Role;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.data.repository.UserRepository;
import pl.net.malinowski.travelagency.logic.principal.MyUserPrincipal;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        user.setRoles(new HashSet<>(Arrays.asList(roleService.findByName(Role.Type.ROLE_CUSTOMER))));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
}
