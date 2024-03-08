package com.omega.proiects10;

import com.omega.proiects10.SQL.Furnizori;
import com.omega.proiects10.SQL.FurnizoriJdbc;
import com.omega.proiects10.SQL.SQLcustom.Subject7;
import com.omega.proiects10.querys.Query1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.omega.proiects10.SQL")
public class FurnizoriJdbcTest {
    @Autowired
    private FurnizoriJdbc repo;
    @Test
    public void select1Test() {
        Query1 query1 = new Query1();
        query1.setString1("stare");
        query1.setString2("numef");
        query1.setBool1(false);
        query1.setBool2(true);

        List<Furnizori> answer= repo.select1(query1);
        for(Furnizori f: answer)
            System.out.println(f);
    }

    @Test
    public void select7Test() {
        List<Subject7> answer= repo.select7();
        for(Subject7 f: answer)
            System.out.println(f);
    }
}
