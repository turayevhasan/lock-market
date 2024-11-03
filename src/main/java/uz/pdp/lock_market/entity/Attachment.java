package uz.pdp.lock_market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lock_market.entity.time.TimeUUID;
import uz.pdp.lock_market.util.CoreUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attachment")
public class Attachment extends TimeUUID {
    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String contentType;

    public String getFilePath() {
        return CoreUtils.FILE_UPLOAD_PATH + "/" + super.getId() + "." + this.extension;
    }
}
