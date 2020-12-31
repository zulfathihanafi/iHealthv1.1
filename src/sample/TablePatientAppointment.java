package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class TablePatientAppointment {
    LocalTime time;
    LocalDate date;

    public TablePatientAppointment(LocalTime time, LocalDate date) {
        this.time = time;
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
