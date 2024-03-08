package com.omega.proiects10.SQL;

import com.omega.proiects10.SQL.SQLcustom.Subject8;
import com.omega.proiects10.querys.Query2;
import com.omega.proiects10.querys.Query8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComponenteJdbc {

    private JdbcTemplate template;

    @Autowired
    public ComponenteJdbc(JdbcTemplate template) {
        this.template = template;
    }

    public List<Componente> select2(Query2 query2){
        String sql = "SELECT * FROM Componente WHERE masa BETWEEN " + query2.getMasa1() + " AND " + query2.getMasa2() + " AND oras = '" + query2.getOras()  + "'";
        return template.query(sql, new BeanPropertyRowMapper<>(Componente.class));
    }

    public List<Subject8> select8(Query8 query8){
        String sql="SELECT MIN(cantitate) AS cantitate1, MAX(cantitate) AS cantitate2 FROM Livrari WHERE idc = '" + query8.getIdc() + "';";
        return template.query(sql, new BeanPropertyRowMapper<>(Subject8.class));
    }
}
