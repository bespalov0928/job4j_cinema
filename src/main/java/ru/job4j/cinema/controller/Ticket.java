package ru.job4j.cinema.controller;

import java.util.Objects;

public class Ticket {

    private int id;
    private int session_id;
    private int row;
    private int cell;
    private int place;
    private int account_id;

    public Ticket(int session_id, int row, int cell, int place, int account_id) {
        //this.id = id;
        this.session_id = session_id;
        this.row = row;
        this.cell = cell;
        this.place = place;
        this.account_id = account_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSession_id() {
        return session_id;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    public int getPlace() {
        return place;
    }

    public int getAccount_id() {
        return account_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, session_id, row, cell, account_id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                session_id == ticket.session_id &&
                row == ticket.session_id &&
                cell == ticket.cell &&
                account_id == ticket.account_id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", session_id=" + session_id +
                ", row=" + row +
                ", cell=" + cell +
                ", account_id=" + account_id +
                '}';
    }
}
