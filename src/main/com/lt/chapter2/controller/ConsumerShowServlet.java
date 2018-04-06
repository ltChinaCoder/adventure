package com.lt.chapter2.controller;

import com.lt.chapter2.model.Consumer;
import com.lt.chapter2.service.ConsumerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/consumer_show")
public class ConsumerShowServlet extends HttpServlet {
    private ConsumerService consumerService=new ConsumerService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding ("UTF-8");

        resp.setCharacterEncoding ("UTF-8");
        PrintWriter writer = resp.getWriter();
        Long consumerId = Long.valueOf(req.getParameter("consumerId"));
        if(consumerId==null)
        {
            writer.write("id不存在，不可以编辑");
        }
        else
        {
            Consumer consumer = consumerService.getConsumerById(consumerId);
            req.setAttribute("consumer", consumer);
            req.getRequestDispatcher("/jsp/consumer.jsp").forward(req,resp );
        }

    }
}
