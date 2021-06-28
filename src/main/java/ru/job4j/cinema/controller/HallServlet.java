package ru.job4j.cinema.controller;

import com.google.gson.*;

import ru.job4j.cinema.persistence.Ticket;
import ru.job4j.cinema.service.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class HallServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        List<Ticket> tickets = (ArrayList<Ticket>) PsqlStore.instOf().findAllTicket();
        String json = GSON.toJson(tickets.toArray());
        System.out.println(json);
        writer.println(json);
        writer.flush();
//        req.setAttribute("tickets", tickets);
//        req.getRequestDispatcher("index.jsp").forward(req, resp);
        //super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
//        List<Ticket> tickets = (List<Ticket>) PsqlStore.instOf().findAllTicket();
//        resp.sendRedirect(req.getContextPath()+"/index.jsp");

        //super.doPost(req, resp);
    }
}
