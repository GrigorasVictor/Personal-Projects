package com.omega.proiects10.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExceptiiJdbc {

    private JdbcTemplate template;

    @Autowired
    public ExceptiiJdbc(JdbcTemplate template) {
        this.template = template;
    }

    public List<Exceptii> getTable(){
        String sql = "SELECT DISTINCT * " +
                "FROM exceptii";
        return template.query(sql, new BeanPropertyRowMapper<>(Exceptii.class));
    }
}
