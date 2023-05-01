package com.webdev.servlet.filter;

import com.webdev.servlet.dto.MemberDTO;
import com.webdev.servlet.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        log.info("Login Check Filter ...................");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        if (session.getAttribute("loginInfo") != null) {
            chain.doFilter(request, response);
            return;
        }

        // session 에 loginInfo 값이 없다면 쿠키의 remember-me 체크
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // 세션에도 없고 쿠키에도 로그인 인증이 없다면 그냥 로그인 화면으로
        if (cookie == null) {
            res.sendRedirect("/login");
            return;
        }

        // 쿠키에 자동 로그인 정보가 존재하면
        log.info("cookie 는 존재하는 상황");
        // uuid 값
        String uuid = cookie.getValue();

        try {
            // DB 확인
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

            log.info("쿠키의 값으로 조회한 사용자 정보: " + memberDTO);
            if (memberDTO == null) {
                throw new Exception("Cookie value is not valid");
            }
            // 회원 정보를 세션에 추가
            session.setAttribute("loginInfo", memberDTO);
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {

        if (cookies == null || cookies.length == 0) {
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck -> ck.getName().equals(cookieName))
                .findFirst();

        return result.isPresent() ? result.get() : null;
    }
}
