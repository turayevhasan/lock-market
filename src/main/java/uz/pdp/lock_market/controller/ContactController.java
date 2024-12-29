package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.entity.Contact;
import uz.pdp.lock_market.payload.contact.ContactAddReq;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.service.ContactService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.CONTACT)
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/add")
    public ApiResult<ResBaseMsg> addContact(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid ContactAddReq contact) {
        return ApiResult.successResponse(contactService.addContact(lang, contact));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get-all")
    public ApiResult<List<Contact>> getAllContact(
            @RequestParam("read") Boolean read,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResult.successResponse(contactService.getAll(read, page, size));
    }

    @PutMapping("/read/{contactId}")
    public ApiResult<Contact> readContact(
            @PathVariable("contactId") long contactId,
            @RequestParam("read") Boolean read) {
        return ApiResult.successResponse(contactService.readContact(contactId,  read));
    }
}
