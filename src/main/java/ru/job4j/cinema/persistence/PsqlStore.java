package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.util.PSQLException;

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
import ru.job4j.cinema.controller.Account;
import ru.job4j.cinema.controller.Ticket;

public class PsqlStore {
    private final BasicDataSource pool = new BasicDataSource();

    // Инициализация логера
    private static final Logger log = Logger.getLogger(PsqlStore.class);

    public PsqlStore() {
        Properties cfg = new Properties();
        String currentPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        try (BufferedReader io = new BufferedReader(
                new FileReader(currentPath+"/db.properties")
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
                     "holl.id as idHoll,  " +
                     "holl.row as rowHoll, " +
                     "holl.cell as cellHoll, " +
                     "ticket.session_id as ticketSession_id, " +
                     "ticket.row as ticketRow, " +
                     "ticket.cell as ticketCell, " +
                     "holl.id as ticketPlase, " +
                     "ticket.account_id as ticketAccount_id " +
                     "from holl left join ticket " +
                     "on holl.row = ticket.row " +
                     "and holl.cell = ticket.cell " +
                     "order by holl.id")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(
                            it.getInt("ticketSession_id"),
                            it.getInt("rowHoll"),
                            it.getInt("cellHoll"),
                            it.getInt("ticketPlase"),
                            it.getInt("ticketAccount_id")
                    ));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return tickets;
    }

    public int addTicket(Ticket ticket) throws PSQLException, SQLException {
        int idTicket = -1;
        Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "insert into ticket(session_id, row, cell, account_id) VALUES (?, ?, ?, ?)",
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

    public void updateTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE ticket SET session_id = ?, row = ?, cell = ? WHERE account_id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getSession_id());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getAccount_id());
            ps.execute();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public Ticket findTicketById(int accountId) {
        Ticket ticket = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select ticket.id,ticket.session_id" +
                     ",ticket.row" +
                     ",ticket.cell" +
                     ",ticket.account_id" +
                     ",holl.id as place " +
                     "from ticket" +
                     "left join holl on holl.row = ticket.row and holl.cell = ticket.cell" +
                     "where account_id = ?")) {
            ps.setInt(1, accountId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    ticket = new Ticket(
                            it.getInt("session_id"),
                            it.getInt("row"),
                            it.getInt("cell"),
                            it.getInt("place"),
                            it.getInt("account_id")
                    );
                    ticket.setId(it.getInt("id"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ticket;
    }

    public int addAccount(Account account) {
        int idAccount = -1;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "insert into account(username, email, phone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    idAccount = id.getInt(1);
                    account.setId(idAccount);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return idAccount;
    }

    public Account findAccountByPhone(String phone) {
        Account ac = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from account where phone = ?")) {
            ps.setString(1, phone);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    ac = new Account(
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")
                    );
                    ac.setId(it.getInt("id"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ac;
    }
}
