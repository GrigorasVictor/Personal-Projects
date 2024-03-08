package com.omega.proiects10;

import com.omega.proiects10.SQL.Exceptii;
import com.omega.proiects10.SQL.ExceptiiJdbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.omega.proiects10.SQL")
public class ExceptiiJdbcTest {

    @Autowired
    private ExceptiiJdbc repo;

    @Test
    public void getTableTest(){
        List<Exceptii> answer = repo.getTable();

        for(Exceptii i: answer)
            System.out.println(i);
    }
}
