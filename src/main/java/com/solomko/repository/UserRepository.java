package com.solomko.repository;

import com.solomko.SQL.SqlHelper;
import com.solomko.domain.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
    private static Connection connection = SqlHelper.getConnection();


    public User getUser(String userName){
        User user= new User();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from user where LOGIN=?;");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user.setUserId(rs.getInt("ID"));
                user.setName(userName);
            } else {
                user = new User(userName);
                user = save(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User save(User user) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO USER(LOGIN) VALUES (?)");
            ps.setString(1, user.getName());
            ps.execute();

            ps = connection.prepareStatement("SELECT * from user where LOGIN=?;");
            ps.setString(1, user.getName());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user.setUserId(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
