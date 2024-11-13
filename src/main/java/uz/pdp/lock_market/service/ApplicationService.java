package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Application;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.ApplicationMapper;
import uz.pdp.lock_market.payload.app.ApplicationAddReq;
import uz.pdp.lock_market.payload.app.ApplicationRes;
import uz.pdp.lock_market.payload.app.ApplicationUpdateReq;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.repository.ApplicationRepository;
import uz.pdp.lock_market.repository.LockRepository;
import uz.pdp.lock_market.util.FormatPatterns;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final LockRepository lockRepository;

    public ResBaseMsg add(ApplicationAddReq req) {
        Pattern pattern = Pattern.compile(FormatPatterns.MOBILE_PHONE_NUMBER);
        if (!pattern.matcher(req.getPhone()).matches()) {
            throw RestException.restThrow(ErrorTypeEnum.PHONE_NUMBER_NOT_VALID);
        }
        Lock lock = lockRepository.findById(req.getLockId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        Application application = ApplicationMapper.reqToEntity(req, lock);
        applicationRepository.save(application);  //saving

        return new ResBaseMsg("Successfully received your application. We will contact you!");
    }

    public ApplicationRes update(long id, ApplicationUpdateReq req) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.APPLICATION_NOT_FOUND));

        if (req.getLockId() != null) {
            Lock newLock = lockRepository.findById(req.getLockId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));
            app.setLock(newLock);
        }
        ApplicationMapper.update(app, req);
        applicationRepository.save(app); //updating

        return ApplicationMapper.entityToDto(app);
    }

    public ApplicationRes getOne(long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.APPLICATION_NOT_FOUND));

        return ApplicationMapper.entityToDto(application);
    }

    public List<ApplicationRes> findAll(int page, int size, Boolean active){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        return applicationRepository.findAllByFilters(active, pageable).stream()
                .map(ApplicationMapper::entityToDto)
                .toList();
    }
}
