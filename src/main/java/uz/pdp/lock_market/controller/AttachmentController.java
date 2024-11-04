package uz.pdp.lock_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.file.ResUploadFile;
import uz.pdp.lock_market.service.AttachmentService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.UUID;

@RequestMapping(BaseURI.API1 + BaseURI.FILE)
@RestController
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService service;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(
            value = "/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResult<ResUploadFile> upload(@RequestPart("file") MultipartFile file) {
        return ApiResult.successResponse(service.upload(file));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<?> download(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "inline") String view) {
        return service.download(id, view);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping( "/delete/{id}")
    public ApiResult<ResBaseMsg> deleteFile(@PathVariable UUID id){
        return ApiResult.successResponse(service.delete(id));
    }

}
