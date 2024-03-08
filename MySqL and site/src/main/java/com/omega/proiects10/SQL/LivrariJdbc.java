package com.omega.proiects10.SQL;

import com.omega.proiects10.SQL.SQLcustom.*;
import com.omega.proiects10.querys.Query5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LivrariJdbc {
    private JdbcTemplate template;
    @Autowired
    public LivrariJdbc(JdbcTemplate template) {
        this.template = template;
    }

    public List<FullJoinClasses> select3(){
        String sql = "SELECT p.numep AS numep, cm.numec AS numec, cm.oras AS oras " +
                "FROM Livrari l " +
                "JOIN Componente cm ON cm.idc = l.idc " +
                "JOIN Proiecte p ON p.idp = l.idp " +
                "WHERE cm.oras = p.oras";

        return template.query(sql, new BeanPropertyRowMapper<>(FullJoinClasses.class));
    }

    public List<FullJoinClasses> select4(){ // am folosit idc pe post de idp2
        String sql = "SELECT l1.idp AS idp, l2.idp idc " +
                "FROM Livrari l1 JOIN Livrari l2 ON (l1.idc = l2.idc AND l1.idf = l2.idf) " +
                "WHERE l1.idp < l2.idp";

        return template.query(sql, new BeanPropertyRowMapper<>(FullJoinClasses.class));
    }

    public List<FullJoinClasses> select5(Query5 query5) {
        String oras = query5.getOras();
        String sql = "SELECT cm.numec as numec " +
                "FROM Livrari l " +
                "JOIN Componente cm ON l.idc = cm.idc " +
                "JOIN Proiecte p ON l.idp = p.idp " +
                "WHERE p.oras = '" + oras + "' " +
                "AND l.cantitate = (" +
                "    SELECT MIN(l2.cantitate) " +
                "    FROM Livrari l2 " +
                "    JOIN Proiecte p2 ON l2.idp = p2.idp " +
                "    WHERE p2.oras = '" + oras + "'" +
                ");";

        return template.query(sql, new BeanPropertyRowMapper<>(FullJoinClasses.class));
    }

}
