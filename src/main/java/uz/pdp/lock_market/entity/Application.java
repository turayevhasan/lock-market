package uz.pdp.lock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @Column(nullable = false)
    private Long lockId;

    @Column(nullable = false)
    private Integer lockAmount;

    @Column(nullable = false)
    private Boolean customLogo;

    @Column(nullable = false)
    private Boolean helpSetup;
}
