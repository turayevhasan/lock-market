package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.enums.LockType;
import uz.pdp.lock_market.entity.time.TimeLong;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Attachment> photos;

    @OneToOne(fetch = FetchType.LAZY)
    private Feature feature;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LockType lockType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lock")
    private List<Commentary> commentaries;
}
