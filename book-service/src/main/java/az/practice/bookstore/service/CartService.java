package az.practice.bookstore.service;

import az.practice.bookstore.exception.BookNotFoundException;
import az.practice.bookstore.exception.CartNotFoundException;
import az.practice.bookstore.exception.StockLimitException;
import az.practice.bookstore.exception.UserNotFoundException;
import az.practice.bookstore.model.dto.request.CartDto;
import az.practice.bookstore.model.dto.request.CartItemDto;
import az.practice.bookstore.model.dto.response.CartItemResponseDto;
import az.practice.bookstore.model.entity.Book;
import az.practice.bookstore.model.entity.Cart;
import az.practice.bookstore.model.entity.CartItem;
import az.practice.bookstore.model.entity.Users;
import az.practice.bookstore.model.mapper.BookMapper;
import az.practice.bookstore.model.mapper.CartItemMapper;
import az.practice.bookstore.repository.BookRepository;
import az.practice.bookstore.repository.CartItemRepository;
import az.practice.bookstore.repository.CartRepository;
import az.practice.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookMapper bookMapper;

    @Transactional
    public void addToCart(Long userId, Long bookId, CartDto cartDto) throws StockLimitException {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));

        int requestedQuantity = cartDto.getQuantity();


        if (requestedQuantity > book.getStockQuantity()) {
            throw new StockLimitException("requested quantity more than grater stock quantity.");
        }
        if (requestedQuantity == 0) {
            throw new CartNotFoundException("quantity can not be zero");
        }


        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUsers(user);
            user.setCart(cart);
            book.setCarts(cart);

        }

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getBooks().getId().equals(bookId))
                .findFirst();

        CartItem cartItem;
        double totalPrice = book.getPrice() * cartDto.getQuantity();
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartDto.getQuantity());
            cartItem.setTotalPrice(totalPrice);
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(cartDto.getQuantity());
            cartItem.setBooks(book);
            cartItem.setCart(cart);
            cartItem.setTotalPrice(totalPrice);
            cart.getCartItems().add(cartItem);
        }
//        cartRepository.save(cart);

        cartItemRepository.save(cartItem);

        userRepository.save(user);
    }

    @Transactional
    public void deleteCart(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cart -> {
            Users users = cart.getUsers();
            if (users != null) {
                users.setCart(null);
            }
            cart.getBooks().forEach(book -> {
                book.setCarts(null);
            });
            cartRepository.delete(cart);
        }, () -> {
            throw new CartNotFoundException("Cart not found with id: " + id);
        });
    }

    public void updateCart(Long id, CartItemDto cartItemDto) {
        CartItem oldCartItem = cartItemRepository.findByCartId(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + id));
        if (oldCartItem != null) {
            CartItem updateCartItem = cartItemMapper.mapToEntity(cartItemDto);
            updateCartItem.setCart(oldCartItem.getCart().getUsers().getCart());
            updateCartItem.setBooks(oldCartItem.getBooks());
            updateCartItem.setId(oldCartItem.getId());
            double totalPrice = updateCartItem.getBooks().getPrice() * cartItemDto.getQuantity();
            updateCartItem.setTotalPrice(totalPrice);

            cartItemRepository.save(updateCartItem);

        }
    }


    public CartItemResponseDto getCartById(Long id) {
        CartItem oldCartItem = cartItemRepository.findByCartId(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + id));
        CartItemResponseDto cartItemResponseDto = cartItemMapper.CartItemResponseDto(oldCartItem);
        CartItemResponseDto bookDto = bookMapper.CartItemResponseDto(oldCartItem.getBooks());
        cartItemResponseDto.setAuthor(bookDto.getAuthor());
        cartItemResponseDto.setTitle(bookDto.getTitle());
        return cartItemResponseDto;

    }
}
