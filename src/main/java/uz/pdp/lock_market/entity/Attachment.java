package uz.pdp.lock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lock_market.entity.base.BaseEntityUUID;
import uz.pdp.lock_market.entity.base.TimeUUID;
import uz.pdp.lock_market.util.BaseConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attachment")
public class Attachment extends BaseEntityUUID {
    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String contentType;

    public String getFilePath() {
        return BaseConstants.FILE_UPLOAD_PATH + super.getId() + "." + this.extension;
    }
}
