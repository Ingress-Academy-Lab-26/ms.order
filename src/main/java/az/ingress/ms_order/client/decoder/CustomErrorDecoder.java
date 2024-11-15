package az.ingress.ms_order.client.decoder;

import az.ingress.ms_order.exception.CustomFeignException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import static az.ingress.ms_order.client.decoder.JsonNodeFieldName.CODE;
import static az.ingress.ms_order.client.decoder.JsonNodeFieldName.MESSAGE;
import static az.ingress.ms_order.model.enums.ExceptionMessages.CLIENT_ERROR;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        var errorMessage = CLIENT_ERROR.getMessage();
        var errorCode = CLIENT_ERROR.getCode();

        JsonNode jsonNode;
        try(var body = response.body().asInputStream()) {
            jsonNode = new ObjectMapper().readValue(body, JsonNode.class);
        }catch (Exception e){
            throw new CustomFeignException(CLIENT_ERROR.getMessage(), response.status(), CLIENT_ERROR.getCode());
        }

        if (jsonNode.has(MESSAGE.getValue())){
            errorMessage = jsonNode.get(MESSAGE.getValue()).asText();
        }

        if (jsonNode.has(CODE.getValue())){
            errorCode = jsonNode.get(CODE.getValue()).asText();
        }

        log.error("ActionLog.decode.error Message: {}, Code: {}, Method: {}", errorMessage, errorCode, methodKey);
        return new CustomFeignException(errorMessage, response.status(), errorCode);
    }


}
