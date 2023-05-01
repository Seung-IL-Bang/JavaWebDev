package com.webdev.servlet.controller;

import com.webdev.servlet.dto.TodoDTO;
import com.webdev.servlet.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
@WebServlet(name = "todoRegisterController", value = "/todo/register")
public class TodoRegisterController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;
    private final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("/todo/register GET ..................................");

        HttpSession session = request.getSession();

        // 기존에 JSESSIONID 가 없는 새로운 사용자
        if (session.isNew()) {
            log.info("JSESSIONID 쿠키가 새로 만들어진 사용자");
            response.sendRedirect("/login");
            return;
        }

        // JSESSIONID 는 있지만 해당 세션 컨텍스트에 loginInfo 라는 이름으로 저장된 객체가 없는 경우
        if (session.getAttribute("loginInfo") == null) {
            log.info("로그인한 정보가 없는 사용자");
            response.sendRedirect("/login");
            return;
        }

        // 로그인한 정보가 있는 정상적인 경우라면 등록 화면으로 이동
        request.getRequestDispatcher("/WEB-INF/todo/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TodoDTO todoDTO = TodoDTO.builder()
                .title(request.getParameter("title"))
                .dueDate(LocalDate.parse(request.getParameter("dueDate"), DATEFORMATTER))
                .build();

        log.info("/todo/register POST ...........................");
        log.info(todoDTO);
        try {
            todoService.register(todoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/todo/list");
    }


}
