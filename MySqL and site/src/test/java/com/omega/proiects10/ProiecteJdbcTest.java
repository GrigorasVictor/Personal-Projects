package com.omega.proiects10;

import com.omega.proiects10.SQL.LivrariJdbc;
import com.omega.proiects10.SQL.Proiecte;
import com.omega.proiects10.SQL.ProiecteJdbc;
import com.omega.proiects10.SQL.SQLcustom.FullJoinClasses;
import com.omega.proiects10.querys.Query6;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.omega.proiects10.SQL")
public class ProiecteJdbcTest {

    @Autowired
    private ProiecteJdbc repo;

    @Test
    public void select6Test(){
        Query6 query6 = new Query6();
        query6.setIdf("F001");
        List<Proiecte> answer = repo.select6(query6);

        for(Proiecte i: answer)
            System.out.println(i);
    }
}
