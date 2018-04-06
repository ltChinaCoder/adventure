package com.lt.chapter2.controller;

import com.lt.chapter2.model.Consumer;
import com.lt.chapter2.service.ConsumerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/consumer")
public class ConsumerServlet extends HttpServlet {
    private ConsumerService consumerService;

    @Override
    public void init() throws ServletException {
        consumerService=new ConsumerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Consumer> consumerList = consumerService.getConsumerList();
        req.setAttribute("consumerList", consumerList);
        req.getRequestDispatcher("/jsp/consumers.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
