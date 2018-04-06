package com.lt.chapter2.controller;

import com.lt.chapter2.service.ConsumerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/consumer_edit")
public class ConsumerEditServlet extends HttpServlet {
    private ConsumerService consumerService=new ConsumerService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding ("UTF-8");

        resp.setCharacterEncoding ("UTF-8");
        final Enumeration<String> attributeNames = req.getParameterNames();
        Map<String, Object> map= new HashMap<String, Object>();
        while(attributeNames.hasMoreElements())
        {
            String s = attributeNames.nextElement();
            map.put(s, req.getParameter(s));
        }
        boolean consumerId = consumerService.updateConsumer(Long.valueOf(req.getParameter("consumerId")), map);
        PrintWriter writer = resp.getWriter();
        String res=consumerId?"修改成功":"修改失败";
        writer.write(res);
        resp.sendRedirect("/consumer");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
