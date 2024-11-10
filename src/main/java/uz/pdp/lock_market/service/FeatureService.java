package uz.pdp.lock_market.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.feature.req.ReqFeature;
import uz.pdp.lock_market.payload.feature.res.ResFeature;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {


    public ResFeature add(ReqFeature req) {

        return null;
    }

    public ResFeature update(Long id, ReqFeature req) {
        return null;
    }

    public ResBaseMsg delete(Long id) {
        return null;
    }

    public ResFeature get(long id) {

        return null;
    }

    public List<ResFeature> getAll() {

        return null;
    }
}
