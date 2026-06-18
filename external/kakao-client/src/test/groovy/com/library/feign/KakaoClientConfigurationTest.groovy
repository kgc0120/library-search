package com.library.feign

import feign.RequestTemplate
import spock.lang.Specification

class KakaoClientConfigurationTest extends Specification {

    KakaoClientConfiguration kakaoClientConfiguration

    void setup(){
        kakaoClientConfiguration = new KakaoClientConfiguration()
    }

    def "requestInterceptor의 header에 key 값들이 적용된다."() {
        given:
        def template = new RequestTemplate()
        def restApiKey = "key"

        and:"interceptor를 타기 전에는 header가 없다."
        template.headers()["Authorization"] == null

        when:"interceptor를 탄다."
        def interceptor = kakaoClientConfiguration.requestInterceptor(restApiKey)
        interceptor.apply(template)

        then: "interceptor를 탄 이후에 header가 추가된다"
        template.headers()["Authorization"].contains("KakaoAK " + restApiKey)
    }
}
