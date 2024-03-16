package az.practice.bookstore.controller;

import az.practice.bookstore.exception.EmptyCartException;
import az.practice.bookstore.exception.StockLimitException;
import az.practice.bookstore.model.dto.request.OrderDetailsDto;
import az.practice.bookstore.model.dto.request.OrderDto;
import az.practice.bookstore.model.dto.response.OrderDetailsWithLocalDate;
import az.practice.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto,
                                                @RequestParam Long userId,
                                                @RequestParam Long booksId) throws StockLimitException {
        return new ResponseEntity<>(orderService.createOrder(orderDto, userId, booksId), HttpStatus.CREATED);
    }

    @PostMapping("/create/Order/From/Cart/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> createOrderFromCart(@PathVariable Long userId) throws StockLimitException, EmptyCartException {
        orderService.placeOrderFromCart(userId);
        return ResponseEntity.ok("Success");
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrderById(id));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<OrderDetailsWithLocalDate>> getOrderDetailsByOrderId(@PathVariable Long id) {
        List<OrderDetailsWithLocalDate> orderDetails = orderService.getOrderDetailsByOrderId(id);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDetailsWithLocalDate>> getAllOrderDetails() {
        List<OrderDetailsWithLocalDate> orderDetails = orderService.getAllOrderDetails();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/user/All/Time/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<OrderDetailsDto>> getOrdersByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.getOrdersTotalAmountByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/differentTime/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<OrderDetailsWithLocalDate>> getDifferentTimeOrdersByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.getDifferentTimeOrdersByUserId(userId), HttpStatus.OK);
    }


}
