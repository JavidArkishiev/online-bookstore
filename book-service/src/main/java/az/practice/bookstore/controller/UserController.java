package az.practice.bookstore.controller;

import az.practice.bookstore.model.dto.request.UserDto;
import az.practice.bookstore.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

//    @PostMapping
//    public ResponseEntity<UserDto> addUsers(@RequestBody @Valid UserDto userDto) {
//        return new ResponseEntity<>(userServiceImpl.addUsers(userDto), HttpStatus.CREATED);
//    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userServiceImpl.getById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return new ResponseEntity<>(userServiceImpl.getAllUser(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(userServiceImpl.deleteById(id));
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
//    public ResponseEntity<UserDto> updateUserById(@PathVariable Long id,
//                                                  @RequestBody @Valid UserDto requestDto) {
//        return new ResponseEntity<>(userServiceImpl.updateUser(id, requestDto), HttpStatus.OK);
//    }


}
