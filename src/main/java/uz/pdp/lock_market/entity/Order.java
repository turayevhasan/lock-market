package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    private PromoCode promoCode;

    @Column(nullable = false)
    private Long fullPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.IN_PROGRESS;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String checkUz;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String checkRu;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String checkEn;

    private String lang;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderLine> orderLines;
}
