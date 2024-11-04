package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lock_market.entity.Attachment;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.AttachmentMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.file.ResUploadFile;
import uz.pdp.lock_market.repository.AttachmentRepository;
import uz.pdp.lock_market.util.BaseConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static uz.pdp.lock_market.enums.ErrorTypeEnum.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public ResUploadFile upload(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw RestException.restThrow(FILE_NOT_FOUND);

        Attachment attachment = AttachmentMapper.fromMultipartToEntity(file, BaseConstants.FILE_UPLOAD_PATH);
        attachmentRepository.save(attachment); //save file in db

        try {
            Files.copy(file.getInputStream(), Paths.get(attachment.getFilePath()), StandardCopyOption.REPLACE_EXISTING); //save file in memory
        } catch (IOException e) {
            log.error("Failed to copy file", e);
            throw RestException.restThrow(ErrorTypeEnum.ERROR_SAVING_FILE);
        }



        return AttachmentMapper.fromEntityToResDto(attachment);
    }

    public ResponseEntity<?> download(UUID id, String view) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getContentType()))
                    .headers(httpHeaders -> {
                        httpHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
                        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, view + "; filename=" + attachment.getOriginalName());
                    })
                    .body(Files.readAllBytes(Path.of(attachment.getFilePath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResBaseMsg delete(UUID id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(FILE_NOT_FOUND));

        try {
            Files.deleteIfExists(Path.of(attachment.getFilePath()));  // deletes file from system
        } catch (IOException e) {
            throw RestException.restThrow(FILE_CANNOT_DELETED);
        }

        attachmentRepository.deleteById(attachment.getId()); // deletes file from db

        return new ResBaseMsg("File successfully deleted");
    }

}