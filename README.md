# spring-boot-rest
Building REST services with Spring-boot

- security
    cors
        cross origin resource sharing
        : <script></script>로 둘러싸여 있는 스크립트에서 생성된 
            Cross-Site HTTP Requests는 Same Origin Policy를 적용 받기 때문에 
            Cross-Site HTTP Requests가 불가능하다.
        : 등장 배경
             XMLHttpRequest 에 대해서도 Cross-Site HTTP Requests 가 가능해야 한다.
        : 정의
             요청을 받은 웹 서버가 허락하며 크로스 도메인이라도 ajax 로 통신할 수 있다.
        : 어떻게
             Access-Control-Allow-Origin:* 을 서버에서 헤더에 실어서 내려준다.  
    xss
    중간자 공
- restdocs
- spring5


###

spring https setting

1. ssl 인증서 얻기
    - 자체 서명 인증서 
        : keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
            비번 : 

2. 스프링에서 https 사용
3. http 를 https 로 리다이렉션

