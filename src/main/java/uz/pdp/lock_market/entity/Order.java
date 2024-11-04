package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.template.TimeLong;
import uz.pdp.lock_market.enums.OrderStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends TimeLong {
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerSurname;

    @Column(nullable = false)
    private String CustomerPhone;

    @Column(nullable = false)
    private String CustomerEmail;

    @Column(nullable = false)
    private String deliveryType;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private Boolean setupLock;

    @Column(nullable = false)
    private Boolean installSoft;

    @Column(nullable = false)
    private Long orderPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.IN_PROGRESS;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderLine> orderLines;
}
