package com.example.repositories;

import com.example.domain.models.User;

import java.sql.*;


//This method writes to the DB scheme users. It will enter the values connected to the provided user object.
public class UserRepository {

    public void dbWrite(User user)/* throws LoginSampleException*/ {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO users (email, password, first_name, last_name, address, age, phone_number)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(6, user.getCompanyName());
            ps.setString(7, user.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //This method return a specific user that matches the email entered.
    public User returnUserByEmail(String email) {
        User tmp = new User();
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM users WHERE (email='" + email + "')";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tmp.setEmail(rs.getString(1));
                tmp.setPassword(rs.getString(2));
                tmp.setFirstName(rs.getString(3));
                tmp.setLastName(rs.getString(4));
                tmp.setCompanyName(rs.getString(5));
                tmp.setPhoneNumber(rs.getString(6));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tmp;
    }

    public ResultSet dbRead() {
        ResultSet resSet = null;
        String select = "SELECT email, password, first_name, last_name, age, address, phone_number FROM users";
        try {
            PreparedStatement ps = DBManager.getConnection().prepareStatement(select);
            resSet = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getAllUsersFromDB() {
        ResultSet resSet = null;
        String select = "SELECT email FROM users";
        try {
            PreparedStatement ps = DBManager.getConnection().prepareStatement(select);
            resSet = ps.executeQuery();
        } catch (SQLException e) {   // catches an exception, if we make user number 1
            e.printStackTrace();
        }
        return resSet;
    }

}
