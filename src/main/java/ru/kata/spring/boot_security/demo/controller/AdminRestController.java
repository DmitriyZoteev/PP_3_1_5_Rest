package ru.kata.spring.boot_security.demo.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<User> saveNewUser(@RequestBody User user, BindingResult bindingResult) {
        try {
            userService.saveNewUser(user);
        } catch (DataIntegrityViolationException e) {
            if (Objects.requireNonNull(e.getRootCause()).toString().contains('\'' + user.getUsername() + '\'')) {
                bindingResult.addError(new ObjectError("user", user.getUsername() + " уже существует"));
            }
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> editUser(@RequestBody User user, @PathVariable Long id, BindingResult bindingResult) {
        if (!user.getNewPassword().isBlank()) {
            user.setPassword(user.getNewPassword());
        }
        try {
            userService.editUser(user);
        } catch (DataIntegrityViolationException e) {
            if (Objects.requireNonNull(e.getRootCause()).toString().contains('\'' + user.getUsername() + '\'')) {
                bindingResult.addError(new ObjectError("user", user.getUsername() + " уже существует"));
            }
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/currentUser")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }
}
