package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        Connection con = null;
        Statement statement = null;
        Savepoint savepoint = null;

        String createTable = "CREATE TABLE USERS("
                + "ID INT AUTO_INCREMENT, "
                + "NAME VARCHAR(20) NOT NULL,"
                + "LASTNAME VARCHAR(50) NOT NULL,"
                + "AGE TINYINT NOT NULL," + "PRIMARY KEY (ID)"
                + ")";
        try{
            con = Util.connect();
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("One");
            statement = con.createStatement();
            statement.execute(createTable);
            System.out.println("Table create");
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
            con.rollback(savepoint);
        }finally {
            try{
                con.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection con = null;
        Statement statement = null;
        Savepoint savepoint = null;

        String dropTable = "DROP TABLE USERS";
        try{
            con = Util.connect();
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("Two");
            statement = con.createStatement();
            statement.execute(dropTable);
            System.out.println("Table delete");
            con.commit();
            con.setAutoCommit(true);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            con.rollback(savepoint);
        }finally {
            try{
                con.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        Savepoint savepoint = null;

        String save = "INSERT INTO USERS"
                + "(NAME,LASTNAME,AGE)" +"VALUES"
                + "(?,?,?)";

        try{
            con = Util.connect();
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("Three");

            statement = con.prepareStatement(save);
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.execute();
            System.out.println("User " + name + " " + lastName + " " + age + " add.");

            con.commit();
            con.setAutoCommit(true);

        }catch (SQLException e){
            System.out.println(e.getMessage());
            con.rollback(savepoint);
        }finally {
            try{
                con.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void removeUserById(long id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        Savepoint savepoint = null;

        String remove = "DELETE FROM USERS WHERE ID=?";


        try{
            con = Util.connect();
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("Four");

            statement = con.prepareStatement(remove);
            statement.setLong(1,id);
            statement.execute();

            con.commit();
            con.setAutoCommit(true);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            con.rollback(savepoint);
        }finally {
            try{
                con.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() throws SQLException {
        Connection con = null;
        Statement statement = null;
        Savepoint savepoint = null;
        List<User> users = new ArrayList<>();

        String getAll = "SELECT * FROM USERS";


        try{
            con = Util.connect();
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("Five");

            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(getAll);
            while (rs.next()){
                String name = rs.getString("NAME");
                String lastName = rs.getString("LASTNAME");
                byte age = rs.getByte("AGE");
                User user = new User(name, lastName, age);
                users.add(user);
            }
            con.commit();
            con.setAutoCommit(true);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            con.rollback(savepoint);
        }finally {
            try{
                con.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        Connection con = null;
        Statement statement = null;
        Savepoint savepoint = null;

        String cleanTable = "TRUNCATE TABLE USERS";
        try{
            con = Util.connect();
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("Six");
            statement = con.createStatement();
            statement.execute(cleanTable);
            System.out.println("Table clean");
            con.commit();
            con.setAutoCommit(true);

        }catch (SQLException e){
            System.out.println(e.getMessage());
            con.rollback(savepoint);
        }finally {
            try{
                con.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
}
