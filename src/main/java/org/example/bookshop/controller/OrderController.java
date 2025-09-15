package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.order.CreateOrderRequestDto;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.dto.order.PatchOrderDto;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.order.OrderItemRepository;
import org.example.bookshop.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create order", description = "Create a new order")
    @PostMapping
    public OrderDto createOrder(
            @RequestBody @Valid CreateOrderRequestDto requestDto, Authentication authentication) {
        return orderService.save(findUserId(authentication), requestDto);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "View orders", description = "View all orders")
    @GetMapping
    public List<OrderDto> getOrders(Authentication authentication) {
        return orderService.getOrders(findUserId(authentication));
    }

    @Operation(summary = "Get order", description = "Get one order by id")
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public OrderDto getOrderById(@PathVariable Long orderId, Authentication authentication) {
        return orderService.getOrderById(orderId, findUserId(authentication));
    }

    @Operation(summary = "Get item", description = "Get one item in order by id")
    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public OrderItemDto getItemByIdInOrder(@PathVariable Long orderId,
                                           @PathVariable Long itemId,
                                           Authentication authentication) {
        return orderService.getItemByIdInOrder(orderId, itemId, findUserId(authentication));
    }

    @Operation(summary = "Change status", description = "Change a status order")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public OrderDto changedStatus(@PathVariable Long id,
                                  @RequestBody @Valid PatchOrderDto requestDto) {
        return orderService.changedStatus(id, requestDto);
    }

    @Operation(summary = "Get all OrderItems",
            description = "Retrieve all OrderItems for a specific order)")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getOrderItemsByOrderId(
            @PathVariable(name = "orderId") Long orderId) {
        return orderItemRepository.getOrderItemsByOrderId(orderId);
    }

    private Long findUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
