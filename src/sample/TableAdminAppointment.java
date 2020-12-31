package sample;

import java.time.LocalDate;
import java.time.LocalTime;

public class TableAdminAppointment {
    String num,patient_id;
    LocalDate date;
    LocalTime time;

    public TableAdminAppointment(String num, String patient_id, LocalDate date, LocalTime time) {
        this.num = num;
        this.patient_id = patient_id;
        this.date = date;
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
