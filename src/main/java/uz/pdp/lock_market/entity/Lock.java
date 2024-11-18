package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.pdp.lock_market.enums.LockType;
import uz.pdp.lock_market.entity.template.TimeLong;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lock")
public class Lock extends TimeLong {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<UUID> photoIds;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LockType lockType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lock")
    private List<Comment> comments;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lock")
    private Feature feature;


}
