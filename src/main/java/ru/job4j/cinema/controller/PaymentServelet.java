package ru.job4j.cinema.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;
import ru.job4j.cinema.persistence.Account;
import ru.job4j.cinema.persistence.Ticket;
import ru.job4j.cinema.service.PsqlStore;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/error")
public class PaymentServelet extends HttpServlet {

    // Инициализация логера
    private static final Logger log = Logger.getLogger(PsqlStore.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("doGet");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PrintWriter out = new PrintWriter(resp.getOutputStream());

        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String in = br.readLine();
        JSONObject jsonObject = new JSONObject(in);
        Map<String, Object> map = jsonObject.toMap();

        String row = (String) map.get("row");
        Integer rowValue = Integer.valueOf(row.split("=")[1]);

        String col = (String) map.get("col");
        Integer colValue = Integer.valueOf(col.split("=")[1]);

        String username = (String) map.get("username");
        String usernameValue = username.split("=")[1];

        String phone = (String) map.get("phone");
        String phoneValue = phone.split("=")[1];

        String email = (String) map.get("email");
        String emailValue = email.split("=")[1];

        String place = (String) map.get("place");
        Integer placelValue = Integer.valueOf(place.split("=")[1]);

        int idAccount = -1;
        Account ac = PsqlStore.instOf().getAccount(phoneValue);
        if (ac == null) {
            Account account = new Account(usernameValue,emailValue,phoneValue);
            idAccount = PsqlStore.instOf().setAccount(account);
        } else {
            idAccount = ac.getId();
        }

        Ticket ticketNew = new Ticket(1, rowValue, colValue, placelValue, idAccount);
        //Ticket ticketNew = new Ticket(1, 1, 1, placelValue, idAccount);
        try {
            PsqlStore.instOf().setTicket(ticketNew);
        } catch (Throwable e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Билет уже куплен");
            out.print(e.getMessage());
            out.flush();
            return;
        }

        String text = "Ticket bay";
        String json = GSON.toJson(text);
        System.out.println(json);
        out.println(json);
        out.flush();
    }
}
