package uz.pdp.lock_market.mapper;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lock_market.entity.Attachment;
import uz.pdp.lock_market.payload.file.FileRes;

public interface AttachmentMapper {
    static Attachment fromMultipartToEntity(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return Attachment.builder()
                .originalName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .extension(extension)
                .fileSize(file.getSize())
                .build();
    }
}
