package az.practice.bookstore.service;

import az.practice.bookstore.exception.AddressNotfoundException;
import az.practice.bookstore.exception.ExistsUserException;
import az.practice.bookstore.exception.UserNotFoundException;
import az.practice.bookstore.model.dto.request.AddressDto;
import az.practice.bookstore.model.entity.Address;
import az.practice.bookstore.model.entity.Users;
import az.practice.bookstore.model.mapper.AddressMapper;
import az.practice.bookstore.repository.AddressRepository;
import az.practice.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    public AddressDto createAddress(Long userId, AddressDto addressDto) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found :" + userId));
        if (addressRepository.existsByUsersId(userId)) {
            throw new ExistsUserException("this userId already have a address ");
        }
        Address address = addressMapper.mapToAddressEntity(addressDto);
        users.setAddress(address);
        address.setUsers(users);
        addressRepository.save(address);
        return addressDto;

    }

    public AddressDto updateAddress(Long userId, AddressDto addressDto) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found :" + userId));
        if (users != null) {
            Address updateAddress = addressMapper.mapToAddressEntity(addressDto);
            updateAddress.setId(users.getAddress().getId());
            updateAddress.setUsers(users);
            users.setAddress(updateAddress);
            addressRepository.save(updateAddress);
        }
        return addressDto;
    }

    public void deleteAddress(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found :" + userId));
        Address address = users.getAddress();
        if (address == null) {
            throw new AddressNotfoundException("address not found");
        }
        users.setAddress(null);

        addressRepository.deleteById(address.getId());
    }

    public AddressDto getAddressById(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        return addressMapper.mapToDto(users.getAddress());
    }

    public List<AddressDto> getAllAddress() {
        List<Address> addressList = addressRepository.findAll();
        return addressMapper.mapToAddressDtoList(addressList);
    }
}
