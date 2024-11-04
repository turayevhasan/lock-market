package uz.pdp.lock_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lock_market.entity.template.TimeUUID;
import uz.pdp.lock_market.enums.UserStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_user")
@Entity
public class User extends TimeUUID {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false)
    private Role role;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.INACTIVE;

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }
}
