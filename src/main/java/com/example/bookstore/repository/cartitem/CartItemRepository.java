package com.example.bookstore.repository.cartitem;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);

    List<CartItem> findAllByShoppingCart(ShoppingCart shoppingCart, Pageable pageable);
}
