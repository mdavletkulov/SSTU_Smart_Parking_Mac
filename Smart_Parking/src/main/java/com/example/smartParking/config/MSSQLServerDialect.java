package com.example.smartParking.config;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MSSQLServerDialect extends SQLServerDialect {

    public MSSQLServerDialect() {
        super();
        registerColumnType(Types.VARCHAR, "nvarchar($l)");
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.BIT, "bit");
    }

}
