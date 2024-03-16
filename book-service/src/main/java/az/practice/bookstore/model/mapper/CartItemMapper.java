package az.practice.bookstore.model.mapper;

import az.practice.bookstore.model.dto.request.CartItemDto;
import az.practice.bookstore.model.dto.response.CartItemResponseDto;
import az.practice.bookstore.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem mapToEntity(CartItemDto cartItemDto);

    CartItemResponseDto CartItemResponseDto(CartItem oldCartItem);
}
