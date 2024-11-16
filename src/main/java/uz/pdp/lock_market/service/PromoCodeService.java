package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.PromoCode;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.PromoCodeMapper;
import uz.pdp.lock_market.payload.promo.PromoCodeUpdateReq;
import uz.pdp.lock_market.payload.promo.PromoCodeAddReq;
import uz.pdp.lock_market.payload.promo.PromoCodeRes;
import uz.pdp.lock_market.repository.PromoCodeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeRes add(PromoCodeAddReq req) {
        if (promoCodeRepository.existsByCode(req.getCode())) {
            throw RestException.restThrow(ErrorTypeEnum.PROMOCODE_ALREADY_EXISTS);
        }

        PromoCode promoCode = PromoCode.builder()
                .code(req.getCode())
                .discountPrice(req.getDiscountPrice())
                .build();

        promoCodeRepository.save(promoCode); //saving promoCode

        return PromoCodeMapper.entityToDto(promoCode);
    }

    public PromoCodeRes update(long id, PromoCodeUpdateReq req) {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.PROMOCODE_NOT_FOUND));

        PromoCodeMapper.update(promoCode, req);  //updating
        promoCodeRepository.save(promoCode);

        return PromoCodeMapper.entityToDto(promoCode);
    }

    public PromoCodeRes getOne(long id) {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.PROMOCODE_NOT_FOUND));

        if (!promoCode.getActive())
            throw RestException.restThrow(ErrorTypeEnum.PROMOCODE_IS_NOT_VALID);

        return PromoCodeMapper.entityToDto(promoCode);
    }

    public List<PromoCodeRes> getAll(int page, int size, Long minDiscountPrice, Long maxDiscountPrice, Boolean active) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));

        Page<PromoCode> all = promoCodeRepository.findAllByFilter(active, minDiscountPrice, maxDiscountPrice, pageable);
        return all
                .stream()
                .map(PromoCodeMapper::entityToDto)
                .toList();
    }

}