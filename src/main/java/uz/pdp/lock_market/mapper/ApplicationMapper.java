package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Application;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.payload.app.req.ApplicationAddReq;
import uz.pdp.lock_market.payload.app.res.ApplicationRes;
import uz.pdp.lock_market.payload.app.req.ApplicationUpdateReq;
import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface ApplicationMapper {
    static Application reqToEntity(ApplicationAddReq req, Lock lock) {
        return Application.builder()
                .name(req.getName())
                .phone(req.getPhone())
                .company(req.getCompany())
                .lock(lock)
                .lockAmount(req.getLockAmount())
                .customLogo(req.getCustomLogo())
                .helpSetup(req.getHelpSetup())
                .active(true)
                .build();
    }

    static ApplicationRes entityToDto(Application app) {
        return ApplicationRes.builder()
                .id(app.getId())
                .name(app.getName())
                .phone(app.getPhone())
                .company(app.getCompany())
                .lockId(app.getLock().getId())
                .lockAmount(app.getLockAmount())
                .customLogo(app.getCustomLogo())
                .helpSetup(app.getHelpSetup())
                .active(app.getActive())
                .createdAt(app.getCreatedAt())
                .updatedAt(app.getUpdatedAt())
                .build();
    }

    static void update(Application app, ApplicationUpdateReq req) {
        app.setName(getIfExists(req.getName(), app.getName()));
        app.setPhone(getIfExists(req.getPhone(), app.getPhone()));
        app.setCompany(getIfExists(req.getCompany(), app.getCompany()));
        app.setLockAmount(getIfExists(req.getLockAmount(), req.getLockAmount()));
        app.setCustomLogo(getIfExists(req.getCustomLogo(), app.getCustomLogo()));
        app.setHelpSetup(getIfExists(req.getHelpSetup(), app.getHelpSetup()));
        app.setActive(getIfExists(req.getActive(), app.getActive()));
    }
}
