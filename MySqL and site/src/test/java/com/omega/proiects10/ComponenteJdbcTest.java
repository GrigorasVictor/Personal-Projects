package com.omega.proiects10;

import com.omega.proiects10.SQL.Componente;
import com.omega.proiects10.SQL.ComponenteJdbc;
import com.omega.proiects10.SQL.SQLcustom.Subject8;
import com.omega.proiects10.querys.Query2;
import com.omega.proiects10.querys.Query8;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.omega.proiects10.SQL")
public class ComponenteJdbcTest {
    @Autowired
    private ComponenteJdbc repo;

    @Test
    public void Select2Test(){
        Query2 query2 = new Query2();
        query2.setMasa1(100);
        query2.setMasa2(500);
        query2.setOras("Cluj-Napoca");

        List<Componente> answer= repo.select2(query2);
        for(Componente i:answer)
            System.out.println(i);
    }

    @Test
    public void Select8Test(){
        Query8 query8 = new Query8();
        query8.setIdc("C12");

        List<Subject8> answer= repo.select8(query8);
        for(Subject8 i:answer)
            System.out.println(i);
    }
}
