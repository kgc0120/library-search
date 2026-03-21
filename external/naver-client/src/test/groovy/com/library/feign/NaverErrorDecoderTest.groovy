package com.library.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.library.NaverErrorResponse
import feign.Request
import feign.Response
import spock.lang.Specification

class NaverErrorDecoderTest extends Specification {
    ObjectMapper objectMapper = Mock()
    NaverErrorDecoder errorDecoder = new NaverErrorDecoder(objectMapper)

    def "에러디코더에서 에러발생시 RuntionException 예외가 throw된다."() {
        given:
        def responseBody = Mock(Response.Body)
        def inputStream = new ByteArrayInputStream()
        def response = Response.builder()
                .status(400)
                .request(Request.create(Request.HttpMethod.GET, "http://test.com", [:], null as Request.Body, null))
                .body(responseBody).build()

//        "이 메서드가 N번 호출되는지 검증하고, 호출되면 이 값을 반환해라"
        1 * responseBody.asInputStream() >> inputStream
        1 * objectMapper.readValue(*_) >> new NaverErrorResponse("error!!", "SE03")

        when:
        errorDecoder.decode(_ as String, response)

        then:
        RuntimeException e = thrown()
        e.message == "error!!"
    }


}
