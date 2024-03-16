package az.practice.bookstore.service;

import az.practice.bookstore.enums.OrderStatus;
import az.practice.bookstore.exception.*;
import az.practice.bookstore.model.dto.request.OrderDetailsDto;
import az.practice.bookstore.model.dto.request.OrderDto;
import az.practice.bookstore.model.dto.response.OrderDetailsWithLocalDate;
import az.practice.bookstore.model.entity.*;
import az.practice.bookstore.model.mapper.BookMapper;
import az.practice.bookstore.model.mapper.OrderItemsMapper;
import az.practice.bookstore.model.mapper.OrderMapper;
import az.practice.bookstore.model.mapper.UserMapper;
import az.practice.bookstore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderItemsMapper orderItemsMapper;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto, Long userId, Long booksId) throws StockLimitException {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("this id not found :" + userId));
        Book book = bookRepository.findById(booksId)
                .orElseThrow(() -> new BookNotFoundException("this id not found :" + booksId));

        int requestedQuantity = orderDto.getQuantity();

        if (requestedQuantity > book.getStockQuantity()) {
            throw new StockLimitException("requested quantity more than grater stock quantity.");
        }
        if (requestedQuantity == 0) {
            throw new OrdersNotFoundException("quantity can not be zero");
        }
        double totalPrice = book.getPrice() * orderDto.getQuantity();


        int updatedStockQuantity = book.getStockQuantity() - orderDto.getQuantity();
        book.setStockQuantity(updatedStockQuantity);

        Orders ordersEntity = orderMapper.mapToEntity(orderDto);
        ordersEntity.setUsers(users);
        ordersEntity.setBooks(book);
        ordersEntity.setQuantity(orderDto.getQuantity());
        ordersEntity.setOrderStatus(orderDto.getOrderStatus());
        ordersEntity.setTotalPrice(totalPrice);


        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(requestedQuantity);
        orderItem.setBooks(book);
        orderItem.setOrders(ordersEntity);
        orderItem.setTotalPrice(totalPrice);
        orderItemRepository.save(orderItem);

        orderRepository.save(ordersEntity);


        return orderMapper.mapToDto(ordersEntity);


    }

    public List<OrderDetailsWithLocalDate> getOrderDetailsByOrderId(Long id) {

        List<OrderDetailsWithLocalDate> list = orderRepository.getOrderDetailsById(id);
        if (list.isEmpty()) {
            throw new OrdersNotFoundException("this orderId not found : " + id);
        }
        return list;

    }


    public List<OrderDetailsWithLocalDate> getAllOrderDetails() {
        List<Orders> ordersList = orderRepository.findAll();
        if (ordersList.isEmpty()) {
            throw new OrdersNotFoundException("there are not any orders");
        }
        return ordersList.stream().
                map(orderMapper::OrderDetailsWithLocalDate)
                .collect(Collectors.toList());
    }

    public List<OrderDetailsDto> getOrdersTotalAmountByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found " + userId));

        List<Orders> ordersList = orderRepository.findByUsersId(userId);
        if (ordersList.isEmpty()) {
            throw new UserNotFoundException("Orders not found for user with ID: " + userId);
        }

        Map<String, OrderDetailsDto> orderDetailsMap = new HashMap<>();

        for (Orders order : ordersList) {
            Double totalAmount = order.getTotalPrice();
            String bookTitle = order.getBooks().getTitle();

            orderDetailsMap.compute(bookTitle, (key, existingOrderDetailsDto) -> {
                if (existingOrderDetailsDto == null) {
                    OrderDetailsDto newOrderDetailsDto = new OrderDetailsDto();
                    newOrderDetailsDto.setName(order.getUsers().getFirstName());
                    newOrderDetailsDto.setSurname(order.getUsers().getLastName());
                    newOrderDetailsDto.setBookTitle(bookTitle);
                    newOrderDetailsDto.setQuantity(order.getQuantity());
                    newOrderDetailsDto.setTotalPrice(totalAmount);
                    return newOrderDetailsDto;
                } else {
                    existingOrderDetailsDto.setQuantity(existingOrderDetailsDto.getQuantity() + order.getQuantity());
                    existingOrderDetailsDto.setTotalPrice(existingOrderDetailsDto.getTotalPrice() + totalAmount);
                    return existingOrderDetailsDto;
                }
            });
        }

        return new ArrayList<>(orderDetailsMap.values());
    }


    public String deleteOrderById(Long id) {
        Orders ordersEntity = orderRepository.findById(id).
                orElseThrow(() -> new OrdersNotFoundException("this id not found : " + id));
        orderRepository.deleteById(ordersEntity.getId());
        return "Your orders has deleted";

    }

    @Transactional
    public void placeOrderFromCart(Long userId) throws EmptyCartException, StockLimitException {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Cart cart = user.getCart();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Cannot place an order with an empty cart");
        }

        Orders order = new Orders();
        order.setUsers(user);
        order.setOrderStatus(OrderStatus.PENDING);


        List<OrderItem> orderItems = new ArrayList<>();
//        double totalAmount = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            order.setQuantity(cartItem.getQuantity());
            orderItem.setOrders(order);
            orderItem.setBooks(cartItem.getBooks());
            orderItem.setQuantity(cartItem.getQuantity());
            double totalPrice = cartItem.getBooks().getPrice() * cartItem.getQuantity();
//            totalAmount += totalPrice;
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            if (cartItem.getQuantity() > cartItem.getBooks().getStockQuantity()) {
                throw new StockLimitException("requested quantity more than grater stock quantity");
            }
            if (cartItem.getQuantity() == 0) {
                throw new OrdersNotFoundException("quantity can not be zero");

            }

            Book book = cartItem.getBooks();
            int newStockQuantity = book.getStockQuantity() - cartItem.getQuantity();
            book.setStockQuantity(newStockQuantity);
            order.setBooks(book);
            bookRepository.save(book);

            orderItems.add(orderItem);
            order.setOrderItems(orderItems);
            order.setTotalPrice(totalPrice);

        }


        orderRepository.save(order);
        cartRepository.save(cart);
        userRepository.save(user);
        cartService.deleteCart(cart.getId());  // bu metodu bura ishleden zaman sebeti silir sifarishden sonra.
        //sebete yeni mehsul elave edende ise yeni bir sebet yaranacaq buda databazanin coxluguna sebeb ola biler gelecekde
    }

    public List<OrderDetailsWithLocalDate> getDifferentTimeOrdersByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found " + userId));
        List<Orders> ordersList = orderRepository.findByUsersId(userId);
        if (ordersList.isEmpty()) {
            throw new UserNotFoundException("Orders not found for user with ID: " + userId);
        }
        return ordersList.stream()
                .map(orderMapper::OrderDetailsWithLocalDate)
                .collect(Collectors.toList());
    }

}



