package com.omega.proiects10.SQL;

import com.omega.proiects10.SQL.SQLcustom.FullJoinClasses;
import com.omega.proiects10.SQL.SQLcustom.Subject7;
import com.omega.proiects10.querys.Query1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FurnizoriJdbc {
    private JdbcTemplate template;

    @Autowired
    public FurnizoriJdbc(JdbcTemplate template) {
        this.template = template;
    }

    public List<Furnizori> select1(Query1 query1){
        String ord1 = query1.isBool1() ? "ASC" : "DESC";
        String ord2 = query1.isBool2() ? "ASC" : "DESC";
        String sql = "SELECT * FROM Furnizori ORDER BY " + query1.getString1() + " " + ord1 + ", " + query1.getString2() + " " + ord2;

        return template.query(sql, new BeanPropertyRowMapper<>(Furnizori.class));
    }

    public List<Subject7> select7(){
        String sql = "SELECT COALESCE(f.oras, p.oras, cm.oras) AS ORAS," +
                "COUNT(DISTINCT p.idp) AS NR_PR," +
                "COUNT(DISTINCT cm.idc) AS NR_CMP," +
                "COUNT(DISTINCT f.idf) AS NR_FNR " +
                "FROM Furnizori f " +
                "LEFT JOIN Componente cm ON f.oras = cm.oras " +
                "LEFT JOIN Proiecte p ON f.oras = p.oras " +
                "GROUP BY COALESCE(f.oras, p.oras, cm.oras) " +
                "UNION " +
                "SELECT COALESCE(f.oras, p.oras, cm.oras) AS ORAS," +
                "COUNT(DISTINCT p.idp) AS NR_PR," +
                "COUNT(DISTINCT cm.idc) AS NR_CMP," +
                "COUNT(DISTINCT f.idf) AS NR_FNR " +
                "FROM Furnizori f " +
                "RIGHT JOIN Componente cm ON f.oras = cm.oras " +
                "RIGHT JOIN Proiecte p ON f.oras = p.oras " +
                "GROUP BY COALESCE(f.oras, p.oras, cm.oras)";
        return template.query(sql,  new BeanPropertyRowMapper<>(Subject7.class));
    }
}
