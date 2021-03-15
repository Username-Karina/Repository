package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();

        userDaoJDBC.saveUser("Jane","Austin", (byte) 25);
        userDaoJDBC.saveUser("Date","Masamune",(byte) 67);
        userDaoJDBC.saveUser("Axel","Roses",(byte) 56);
        userDaoJDBC.saveUser("Lary","King",(byte) 35);

        System.out.println(userDaoJDBC.getAllUsers().toString());

        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();


    }
}
