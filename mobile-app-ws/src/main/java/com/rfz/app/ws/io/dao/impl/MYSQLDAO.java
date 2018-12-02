package com.rfz.app.ws.io.dao.impl;

import com.rfz.app.ws.io.dao.DAO;
import com.rfz.app.ws.io.entity.UserEntity;
import com.rfz.app.ws.shared.dto.UserDTO;
import com.rfz.app.ws.utils.HibernateUtils;
import com.rfz.app.ws.com.rfz.app.ws.jdbc.ConnectionManager;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import java.sql.*;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

public class MYSQLDAO implements DAO {
    Session session;
    private final Connection con = ConnectionManager.getConnection();

    @Override
    public void openConnection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO userDto = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entitie
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("email"), userName));

        // Fetch single result
        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
        }

        return userDto;
    }

    public UserDTO getUser(String id) {
        String strSelect = "SELECT * FROM users WHERE userId = ?";
        UserEntity userEntity = new UserEntity();
        UserDTO userDto = new UserDTO();
        try {
            //final  Connection con = ConnectionManager.getConnection();
            final PreparedStatement ps = con.prepareStatement(strSelect);
            ps.setString(1,id);
            final ResultSet rs = ps.executeQuery();
            System.out.println("--->>> The ps = " + ps.toString());
            if (rs.next()) {
                userEntity.setFirstName(rs.getString("firstName"));
                userEntity.setLastName(rs.getString("lastName"));
                userEntity.setEmail(rs.getString("email"));
                userEntity.setId(rs.getLong("id"));
                userEntity.setEncryptedPassword(rs.getString("encryptedPassword"));
                userEntity.setSalt(rs.getString("salt"));
                System.out.println("--->>> in userEntity = " + userEntity.toString());
            }
            System.out.println("--->>> out userEntity = " + userEntity.toString());
            BeanUtils.copyProperties(userEntity, userDto);
            System.out.println("--->>> out userDto = " + userDto.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDto;
    }

    public UserDTO saveUser(UserDTO user)
    {
        UserDTO returnValue = null;
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();

        returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public void closeConnection() {
        if (session != null) {
            session.close();
        }

    }
}
