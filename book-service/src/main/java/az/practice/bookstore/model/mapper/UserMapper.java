package az.practice.bookstore.model.mapper;

import az.practice.bookstore.enums.Role;
import az.practice.bookstore.model.dto.request.UserDto;
import az.practice.bookstore.model.entity.Users;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper {
//    protected PasswordEncoder passwordEncoder;

    //    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")
    @Mapping(target = "role", expression = "java(getDefaultRole())")
    public abstract Users mapToUserEntity(UserDto userDto);

    protected Role getDefaultRole() {
        return Role.USER;
    }


    @Mapping(target = "addressDto", source = "address")
    public abstract UserDto mapToUserDto(Users users);

    public abstract List<UserDto> mapToUserDtoList(List<Users> usersListEntity);
}
