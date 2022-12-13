package br.com.frederykantunnes.challenge.api;

import br.com.frederykantunnes.challenge.api.config.DocumentValidatorAPIConfig;
import br.com.frederykantunnes.challenge.api.dto.DocumentValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProxyDocumentValidator", url = "${integrations.document-validator-url}", configuration = DocumentValidatorAPIConfig.class)
public interface DocumentValidatorAPI {

    @GetMapping(value = "/users/{document}")
    DocumentValidationResponse validateDocument(@PathVariable String document);

}
