package az.practice.bookstore.service.impl;

import az.practice.bookstore.enums.Role;
import az.practice.bookstore.exception.ExistsEmailException;
import az.practice.bookstore.exception.ExistsPhoneNumberException;
import az.practice.bookstore.exception.UserNotFoundException;
import az.practice.bookstore.model.dto.request.AddressDto;
import az.practice.bookstore.model.dto.request.UserDto;
import az.practice.bookstore.model.entity.*;
import az.practice.bookstore.model.mapper.AddressMapper;
import az.practice.bookstore.model.mapper.UserMapper;
import az.practice.bookstore.repository.AddressRepository;
import az.practice.bookstore.repository.CartRepository;
import az.practice.bookstore.repository.OrderRepository;
import az.practice.bookstore.repository.UserRepository;
import az.practice.bookstore.service.CartService;
import az.practice.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;


    public UserDto addUsers(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ExistsEmailException("this email already used " + userDto.getEmail());
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new ExistsPhoneNumberException("this phone number already used");
        }
        Users usersEntity = userMapper.mapToUserEntity(userDto);
        Address addressEntity = addressMapper.mapToAddressEntity(userDto.getAddressDto());
        usersEntity.setAddress(addressEntity);
        addressEntity.setUsers(usersEntity);

        userRepository.save(usersEntity);
        return userDto;
    }

    public UserDto getById(Long id) {
        Users userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This id not found :" + id));
//        AddressDto addressDto = addressMapper.mapToDto(userEntity.getAddress());
        UserDto userDto = userMapper.mapToUserDto(userEntity);
//        userDto.setAddressDto(addressDto);
        return userDto;
    }

    public List<UserDto> getAllUser() {
        List<Users> usersListEntity = userRepository.findAll();
        return usersListEntity.stream()
                .map(users -> {
                    UserDto userDto = userMapper.mapToUserDto(users);
                    AddressDto addressDto = addressMapper.mapToDto(users.getAddress());
//                    userDto.setAddressDto(addressDto);
                    return userDto;

                }).collect(Collectors.toList());

    }

    public String deleteById(Long id) {
        Users usersEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This id not found :" + id));
        try {
            userRepository.delete(usersEntity);
        } catch (StackOverflowError exception) {
            exception.printStackTrace();
        }
        return "Success";
    }


    public UserDto updateUser(Long id, UserDto requestDto) {
        Users oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This id not found :" + id));
        if (oldUser != null) {
            Users updateUsers = userMapper.mapToUserEntity(requestDto);
            Address updateAddress = addressMapper.mapToAddressEntity(requestDto.getAddressDto());
            updateAddress.setId(oldUser.getAddress().getId());
            updateUsers.setId(oldUser.getId());
            updateUsers.setAddress(updateAddress);
            updateAddress.setUsers(updateUsers);
            userRepository.save(updateUsers);
            return requestDto;
        }
        return null;
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };

    }

}
