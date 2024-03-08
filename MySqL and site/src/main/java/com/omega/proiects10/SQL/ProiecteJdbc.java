package com.omega.proiects10.SQL;

import com.omega.proiects10.querys.Query6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProiecteJdbc {
    private JdbcTemplate template;

    @Autowired
    public ProiecteJdbc(JdbcTemplate template) {
        this.template = template;
    }

    public List<Proiecte> select6(Query6 query6){
        String sql = "SELECT numep FROM Proiecte WHERE oras IN (SELECT oras FROM Furnizori WHERE idf ='"+ query6.getIdf() + "')";

        return template.query(sql, new BeanPropertyRowMapper<>(Proiecte.class));
    }
}
