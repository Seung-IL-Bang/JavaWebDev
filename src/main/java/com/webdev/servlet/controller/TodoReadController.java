package com.webdev.servlet.controller;

import com.webdev.servlet.dto.TodoDTO;
import com.webdev.servlet.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet(name = "todoReadController", value = "/todo/read")
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Long tno = Long.parseLong(request.getParameter("tno"));
            TodoDTO dto = todoService.get(tno);

            request.setAttribute("dto", dto);
            request.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(request, response);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }

}
