package com.omega.proiects10;

import com.omega.proiects10.SQL.LivrariJdbc;
import com.omega.proiects10.SQL.SQLcustom.*;
import com.omega.proiects10.querys.Query5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.omega.proiects10.SQL")
public class LivrariJdbcTest {

    @Autowired
    private LivrariJdbc repo;

    @Test
    public void select3Test(){
        List<FullJoinClasses> answer = repo.select3();

        for(FullJoinClasses i: answer)
            System.out.println(i);
    }

    @Test
    public void select4Test(){
        List<FullJoinClasses> answer = repo.select4();

        for(FullJoinClasses i: answer)
            System.out.println(i);
    }

    @Test
    public void select5Test(){
        Query5 query5= new Query5();
        query5.setOras("Bistrița");
        List<FullJoinClasses> answer = repo.select5(query5);

        for(FullJoinClasses i: answer)
            System.out.println(i);
    }

}
