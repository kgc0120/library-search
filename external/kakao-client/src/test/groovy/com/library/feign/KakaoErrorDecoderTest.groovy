package com.library.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.library.ApiException
import com.library.ErrorType
import com.library.KakaoErrorResponse
import feign.Request
import feign.Response
import org.springframework.http.HttpStatus
import spock.lang.Specification

class KakaoErrorDecoderTest extends Specification {

    ObjectMapper objectMapper = Mock()
    KakaoErrorDecoder errorDecoder = new KakaoErrorDecoder(objectMapper)

    def "에러디코더에서 에러발생시 ApiException 예외가 throw된다."() {
        given:
        def responseBody = Mock(Response.Body)
        def inputStream = new ByteArrayInputStream()
        def response = Response.builder()
                .status(400)
                .request(Request.create(Request.HttpMethod.GET, "http://test.com", [:], null as Request.Body, null))
                .body(responseBody).build()

//        "이 메서드가 N번 호출되는지 검증하고, 호출되면 이 값을 반환해라"
        1 * responseBody.asInputStream() >> inputStream
        1 * objectMapper.readValue(*_) >> new KakaoErrorResponse("InvalidArgument", "size more then max")

        when:
        errorDecoder.decode(_ as String, response)

        then:
        ApiException e = thrown()
        verifyAll {
            e.errorMessage == "size more then max"
            e.httpStatus == HttpStatus.BAD_REQUEST
            e.errorType == ErrorType.EXTERNAL_API_ERROR
        }
    }
}
