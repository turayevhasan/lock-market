package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Code;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.repository.CodeRepository;
import uz.pdp.lock_market.util.CoreUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeRepository codeRepository;

    public String generateCode(String email) {
        String generatedCode = CoreUtils.generateSmsCode();

        Optional<Code> found = codeRepository.findFirstByEmailAndDeleted(email, false);
        if (found.isPresent()) {
            found.get().setDeleted(true);
            codeRepository.save(found.get());  //set deleted true if found active code
        }

        Code code = new Code(generatedCode, email);
        codeRepository.save(code);

        return generatedCode;
    }

    public void checkVerify(String email, String inputCode) {
        Code code = codeRepository.findFirstByEmailAndDeleted(email, false)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CODE_NOT_FOUND));

        if (!code.getCode().equals(inputCode)) {
            throw RestException.restThrow(ErrorTypeEnum.INPUT_CODE_NOT_MATCH);
        }

        code.setDeleted(true);
        codeRepository.save(code); // inputCode is true so we will update
    }
}
