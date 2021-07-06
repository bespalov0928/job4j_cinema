package ru.job4j.cinema.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.util.PSQLException;
import ru.job4j.cinema.persistence.Account;
import ru.job4j.cinema.persistence.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PsqlStore {
    private final BasicDataSource pool = new BasicDataSource();

    // Инициализация логера
    private static final Logger log = Logger.getLogger(PsqlStore.class);

    public PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                //new FileReader("db.properties")
                new FileReader("C:\\projects\\job4j_cinema\\db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final PsqlStore INST = new PsqlStore();
    }

    public static PsqlStore instOf() {
        return Lazy.INST;
    }

    public Collection<Ticket> findAllTicket() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select " +
                     "holl.id as idHoll,  " + System.lineSeparator() +
                     "holl.row as rowHoll, " + System.lineSeparator() +
                     "holl.cell as cellHoll, " + System.lineSeparator() +
                     "ticket.session_id as ticketSession_id, " + System.lineSeparator() +
                     "ticket.row as ticketRow, " + System.lineSeparator() +
                     "ticket.cell as ticketCell, " + System.lineSeparator() +
                     "holl.id as ticketPlase, " + System.lineSeparator() +
                     "ticket.account_id as ticketAccount_id " + System.lineSeparator() +
                     "from holl left join ticket " + System.lineSeparator() +
                     "on holl.row = ticket.row " + System.lineSeparator() +
                     "and holl.cell = ticket.cell " + System.lineSeparator() +
                     "order by holl.id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(it.getInt("ticketSession_id"), it.getInt("rowHoll"), it.getInt("cellHoll"), it.getInt("ticketPlase"), it.getInt("ticketAccount_id")));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return tickets;
    }

    public int setTicket(Ticket ticket) throws PSQLException, SQLException{
        int idTicket = -1;
        Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("insert into ticket(session_id, row, cell, account_id) VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ticket.getSession_id());
        ps.setInt(2, ticket.getRow());
        ps.setInt(3, ticket.getCell());
        ps.setInt(4, ticket.getAccount_id());
        ps.execute();
        try (ResultSet id = ps.getGeneratedKeys()) {
            if (id.next()) {
                idTicket = id.getInt(1);
            }
        }
        return idTicket;
    }

//        public void updateTicket (Ticket ticket) {
//            try (Connection cn = pool.getConnection();
//                 PreparedStatement ps = cn.prepareStatement(
//                         "UPDATE ticket SET session_id = ?, row = ?, cell = ? WHERE account_id = ?",
//                         PreparedStatement.RETURN_GENERATED_KEYS)
//            ) {
//                ps.setInt(1, ticket.getSession_id());
//                ps.setInt(2, ticket.getRow());
//                ps.setInt(3, ticket.getCell());
//                ps.setInt(4, ticket.getAccount_id());
//                ps.execute();
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//        }

//        public Ticket getTicket ( int accountId){
//            Ticket ticket = null;
//            try (Connection cn = pool.getConnection();
//                 PreparedStatement ps = cn.prepareStatement("select \n" +
//                         "ticket.id" + System.lineSeparator() +
//                         ",ticket.session_id" + System.lineSeparator() +
//                         ",ticket.row" + System.lineSeparator() +
//                         ",ticket.cell" + System.lineSeparator() +
//                         ",ticket.account_id" + System.lineSeparator() +
//                         ",holl.id as place " + System.lineSeparator() +
//                         "from ticket" + System.lineSeparator() +
//                         "left join holl on holl.row = ticket.row and holl.cell = ticket.cell" + System.lineSeparator() +
//                         "where account_id = ?")) {
//                ps.setInt(1, accountId);
//                try (ResultSet it = ps.executeQuery()) {
//                    while (it.next()) {
//                        ticket = new Ticket(it.getInt("session_id"), it.getInt("row"), it.getInt("cell"), it.getInt("place"), it.getInt("account_id"));
//                        ticket.setId(it.getInt("id"));
//                    }
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            return ticket;
//        }

//        public int setAccount (Account account){
//            int idAccount = -1;
//            try (Connection cn = pool.getConnection();
//                 PreparedStatement ps = cn.prepareStatement(
//                         "insert into account(username, email, phone) VALUES (?, ?, ?)",
//                         PreparedStatement.RETURN_GENERATED_KEYS)
//            ) {
//                ps.setString(1, account.getUsername());
//                ps.setString(2, account.getEmail());
//                ps.setString(3, account.getPhone());
//                ps.execute();
//                try (ResultSet id = ps.getGeneratedKeys()) {
//                    if (id.next()) {
//                        idAccount = id.getInt(1);
//                        account.setId(idAccount);
//                    }
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            return idAccount;
//        }

//        public Account getAccount (String phone){
//            Account ac = null;
//            try (Connection cn = pool.getConnection();
//                 PreparedStatement ps = cn.prepareStatement("select * from account where phone = ?")) {
//                ps.setString(1, phone);
//                try (ResultSet it = ps.executeQuery()) {
//                    while (it.next()) {
//                        ac = new Account(it.getString("username"), it.getString("email"), it.getString("phone"));
//                        ac.setId(it.getInt("id"));
//                    }
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//            }
//            return ac;
//
//        }
//    }
}