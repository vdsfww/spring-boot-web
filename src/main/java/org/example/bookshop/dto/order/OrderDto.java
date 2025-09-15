package org.example.bookshop.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.example.bookshop.model.Status;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private Status status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItemDto> orderItems;
}
