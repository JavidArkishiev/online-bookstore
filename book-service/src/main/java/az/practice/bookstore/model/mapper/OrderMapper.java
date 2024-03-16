package az.practice.bookstore.model.mapper;

import az.practice.bookstore.model.dto.request.OrderDetailsDto;
import az.practice.bookstore.model.dto.request.OrderDto;
import az.practice.bookstore.model.dto.response.OrderDetailsWithLocalDate;
import az.practice.bookstore.model.dto.response.OrderResponseDto;
import az.practice.bookstore.model.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface OrderMapper {
    Orders mapToEntity(OrderDto orderDto);


    OrderResponseDto mapToOrderDto(Orders ordersEntity);

    @Mapping(target = "usersId", source = "users.id")
    @Mapping(target = "booksId", source = "books.id")
    OrderDto mapToDto(Orders orders);


    @Mapping(source = "users.firstName", target = "name")
    @Mapping(source = "users.lastName", target = "surname")
    @Mapping(source = "books.title", target = "bookTitle")
    OrderDetailsDto orderToOrderDetailsDTO(Orders order);

//    @Mapping(source = "users.firstName", target = "name")
//    @Mapping(source = "users.lastName", target = "surname")
//    @Mapping(source = "books.title", target = "bookTitle")
//    List<OrderDetailsDto> orderToOrderDetailsDto(Orders latestOrder);


    @Mapping(source = "users.firstName", target = "name")
    @Mapping(source = "users.lastName", target = "surname")
    @Mapping(source = "books.title", target = "bookTitle")
    List<OrderDetailsDto> mapToDtoList(List<Orders> orderList);


    @Mapping(source = "users.firstName", target = "name")
    @Mapping(source = "users.lastName", target = "surname")
    @Mapping(source = "books.title", target = "bookTitle")
    OrderDetailsWithLocalDate OrderDetailsWithLocalDate(Orders orders);


}

