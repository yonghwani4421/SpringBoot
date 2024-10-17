# SpringBoot
##### 애플리케이션 보안을 담당하는 스프링 하위 프레임워크

#### 인증 인가
- 인증(Authentication) : 사용자가 누구인지 신원을 확인하는 과정이다.
- 인가(Authorization) : 사이트의 특정부분에 접근할 수 있는지 권한을 확인하는 과정이다.

---

#### Spring Security 인증 과정

<p align="center">
  <img src="https://velog.velcdn.com/images/kyungwoon/post/b387ed43-b315-4854-bb11-0c71783683f9/image.png">
</p>

1. 사용자 요청 아이디 패스워드 입력하면, HTTPServletRequest에 아이디와 비밀번호 정보가 전달된다.
2. 유효성 검사가 끝나면 실제 구현체인 UsernamePasswordAuthenticationToken을 만들어 넘겨준다.
3. 전달받은 인증용 객체인 UsernamePasswordAuthenticationToken을 AuthenticationManager에게 보낸다.
4. UsernamePasswordAuthenticationToken을 AuthenticationProvider에 보낸다.
5. 사용자 아이디를 UserDetailService에 보낸다. UserDetailService는 사용자 아이디로 찾은 사용자의 정보를 UserDetails 객체로 만들어 AuthenticationProvider에 전달한다.
6. DB의 사용자 정보를 가져온다.
7. 입력 정보와 UserDetails의 정보를 비교해 실제 인증 처리를 한다.
8. ~ 10. 까지 인증이 완료되면 SecurityContextHolder에 Authenticatin을 저장한다. 인증 여부에 따라서 성공하면 AuthenticationSuccessHandler, 실패하면 AuthenticationFailureHandler를 실행한다.
