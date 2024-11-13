package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.template.TimeLong;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "application")
public class Application extends TimeLong {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lock lock;

    @Column(nullable = false)
    private Integer lockAmount;

    @Column(nullable = false)
    private Boolean customLogo;

    @Column(nullable = false)
    private Boolean helpSetup;

    @Column(nullable = false)
    private Boolean active;
}
