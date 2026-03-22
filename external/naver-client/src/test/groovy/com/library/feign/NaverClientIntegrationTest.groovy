package com.library.feign

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

// 이런 외부 테스트는 결과가 그때그때 달라서 테스트가 불안정할 수 있기 때문에, 일단은 @Ignore 처리해놓고, 필요할 때마다 켜서 테스트하자.
@Ignore
@SpringBootTest(classes = NaverClientIntegrationTest.TestConfig.class)
@ActiveProfiles("test")
class NaverClientIntegrationTest extends Specification {

    @EnableAutoConfiguration
    @EnableFeignClients(clients = NaverClient.class)
    static class TestConfig{}
    
    @Autowired
    NaverClient naverClient
    
    def "네이버 호출"() {
        given:

        when:
        def response = naverClient.search("HTTP", 1, 10)

        then:
        response.total == 38
    }

}
