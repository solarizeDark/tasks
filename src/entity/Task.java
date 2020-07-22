package entity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private Long id;
    private String text;
    private String date;
    private boolean status;

    public Task(String text, String date, boolean status)  {
        this.text = text;
        this.date = date;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                '}';
    }
}