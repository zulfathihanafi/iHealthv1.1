package sample;

import java.time.LocalDate;

public class TableDateException {
    String num;
    LocalDate date;

    public TableDateException(String num, LocalDate date) {
        this.num = num;
        this.date = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
