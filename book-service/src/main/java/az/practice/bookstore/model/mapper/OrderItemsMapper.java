package az.practice.bookstore.model.mapper;

import az.practice.bookstore.model.entity.OrderItem;
import az.practice.bookstore.model.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface OrderItemsMapper {
    OrderItem mapToEntity(Orders ordersEntity);
}
