package az.practice.bookstore.controller;

import az.practice.bookstore.exception.StockLimitException;
import az.practice.bookstore.model.dto.request.CartDto;
import az.practice.bookstore.model.dto.request.CartItemDto;
import az.practice.bookstore.model.dto.response.CartItemResponseDto;
import az.practice.bookstore.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("carts")
public class CartController {
    private final CartService cartService;

    @PostMapping("/addBook")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addBookToCart(@RequestParam Long userId,
                                                @RequestParam Long bookId,
                                                @Valid @RequestBody CartDto cartDto) throws StockLimitException {

        cartService.addToCart(userId, bookId, cartDto);
        return ResponseEntity.ok("Book added to cart successfully.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")

    public ResponseEntity<String> deleteCartById(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok("Success");
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")

    public ResponseEntity<String> updateCart(@PathVariable Long id, @RequestBody CartItemDto cartItemDto) {
        cartService.updateCart(id, cartItemDto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<CartItemResponseDto> getCartById(@PathVariable Long id){
        return new ResponseEntity<>(cartService.getCartById(id), HttpStatus.OK);
    }




}
