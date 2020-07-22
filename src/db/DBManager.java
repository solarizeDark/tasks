package db;

import entity.Task;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public enum DBManager {

    INSTANCE;

    private static final String PATH = "jdbc:sqlite:task.db";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from task";
    private static final String SQL_INSERT = "insert into task(date, text, status) values (?, ?, ?)";
    private static final String SQL_UPDATE = "update task set date = ?, text = ?, status = ? where id = ?";
    private static final String SQL_SELECT_WITH_CONDITION = "select * from task where status = ?";


    private Connection connection;

    DBManager() throws ExceptionInInitializerError {
        try {
            this.connection = DriverManager.getConnection(PATH);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void insert(Task task) {
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, task.getDate());
            preparedStatement.setString(2, task.getText());
            preparedStatement.setBoolean(3, task.isStatus());

            preparedStatement.execute();
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_ALL);

            Long id = null;
            while(result.next()) id = result.getLong("id");
            task.setId(id);

        } catch (SQLException e) {
            throw new IllegalArgumentException();
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {}
            if (result != null)            try { result.close(); } catch (SQLException e) {}
            if (statement != null)         try { statement.close(); } catch (SQLException e) {}
        }
    }

    public void update(Task task) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, task.getDate());
            preparedStatement.setString(2, task.getText());
            preparedStatement.setBoolean(3, task.isStatus());
            preparedStatement.setLong(4, task.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {}
        }
    }

    public List<Task> findAllWithCondition(String dateFrom, String dateTo, Boolean status) {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        List<Task> tasks = new ArrayList<>();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date dateFormatFrom = formatter.parse(dateFrom);
            Date dateFormatTo   = formatter.parse(dateTo);
            preparedStatement = connection.prepareStatement(SQL_SELECT_WITH_CONDITION);
            preparedStatement.setBoolean(1, status);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                Date tempDate = formatter.parse(result.getString("date"));
                if (tempDate.compareTo(dateFormatFrom) >= 0 && tempDate.compareTo(dateFormatTo) <= 0) {
                    Task temp = new Task(
                            result.getString("text"),
                            result.getString("date"),
                            result.getBoolean("status"));
                    temp.setId(result.getLong("id"));
                    tasks.add(temp);
                }
            }
            return tasks;
        } catch (SQLException | ParseException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null)             try { result.close(); } catch (SQLException e) { }
            if (preparedStatement != null)  try { preparedStatement.close(); } catch (SQLException e) { }
        }
    }

}