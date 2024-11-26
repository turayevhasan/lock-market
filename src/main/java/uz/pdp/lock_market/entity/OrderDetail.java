package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.lock_market.entity.base.TimeLong;
import uz.pdp.lock_market.enums.PaymentType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_detail")
public class OrderDetail extends TimeLong {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    /////////////////////////

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String branch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private Boolean setupLock;

    @Column(nullable = false)
    private Boolean installSoft;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "orderDetail")
    private Order order;
}
