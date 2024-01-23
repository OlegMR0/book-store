package com.example.bookstore.service;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.shoppingCart.ShoppingCartRepository;
import com.example.bookstore.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private UserService userService;
    private CartItemService cartItemService;

    public CartItemResponseDto addCartItem(CreateCartItemRequestDto requestDto, Pageable pageable, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getByEmail(email);
        if (!shoppingCartRepository.existsById(user.getId())) {
            addShoppingCartToUser(user);
        }
        return cartItemService.save(requestDto, user);
    }

    private void addShoppingCartToUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        ShoppingCart saved = shoppingCartRepository.save(shoppingCart);

    }

}
