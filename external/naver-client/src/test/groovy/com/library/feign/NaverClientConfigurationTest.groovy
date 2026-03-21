package com.library.feign

import feign.RequestInterceptor
import feign.RequestTemplate
import spock.lang.Specification

class NaverClientConfigurationTest extends Specification {
    NaverClientConfiguration naverClientConfiguration

    void setup(){
        naverClientConfiguration = new NaverClientConfiguration()
    }

    def "requestInterceptor의 header에 key 값들이 적용된다."() {
        given:
        def template = new RequestTemplate()
        def clientId = "id"
        def clientSecret = "secret"

        and:"interceptor를 타기 전에는 header가 없다."
        template.headers()["X-Naver-Client-Id"] == null
        template.headers()["X-Naver-Client-Secret"]== null

        when:"interceptor를 탄다."
        def interceptor = naverClientConfiguration.requestInterceptor(clientId, clientSecret)
        interceptor.apply(template)

        then: "interceptor를 탄 이후에 header가 추가된다"
        template.headers()["X-Naver-Client-Id"].contains(clientId)
        template.headers()["X-Naver-Client-Secret"].contains(clientSecret)
    }


}
