package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Contact;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.payload.contact.ContactAddReq;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.repository.ContactRepository;
import uz.pdp.lock_market.util.FormatPatterns;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ResourceBundleMessageSource messageSource;

    public ResBaseMsg addContact(String lang, ContactAddReq req) {
        Pattern pattern = Pattern.compile(FormatPatterns.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(req.getEmail());
        if (!matcher.matches()) {
            throw RestException.restThrow(ErrorTypeEnum.EMAIL_NOT_VALID);
        }

        Contact contact = new Contact(req.getName(), req.getEmail(), false);
        contactRepository.save(contact);  //saved

        return new ResBaseMsg(messageSource.getMessage("contact.saved", null, Locale.of(lang)));
    }

    public List<Contact> getAll(Boolean read, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return contactRepository.findAllByFilter(read, pageable);
    }

    public Contact readContact(long contactId, Boolean read) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CONTACT_NOT_FOUND));

        contact.setRead(getIfExists(read, contact.isRead()));
        contactRepository.save(contact); //updated

        return contact;
    }
}
