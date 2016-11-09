package com.axway.academy.addressbook.sql.schema;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.internal.util.StringHelper;

/**
 * Extends the standard hibernate dialect for Microsoft SQL Server to support Unicode data.
 */
public class UnicodeSQLServerDialect extends SQLServer2012Dialect {
    
    /**
     * Constructs UnicodeSQLServerDialect object.
     */
    public UnicodeSQLServerDialect() {
        // Use Unicode Characters
        registerColumnType(Types.VARCHAR, "nvarchar($l)");
        registerColumnType(Types.CHAR, "nchar(1)");
        registerColumnType(Types.CLOB, "nvarchar(max)");
        // do not use deprecated image data type for blob columns
        registerColumnType(Types.BLOB, "varbinary(max)");
    }

    /*
     * The purpose of overriding this method is to automatically foreign key column.
     * This way the MySql, Mssql and Oracle schema will be consistent. 
     * 
     */
    @Override
    public String getAddForeignKeyConstraintString(String constraintName, String foreignKey[], String referencedTable, 
            String primaryKey[], boolean referencesPrimaryKey) {
  
        String createForeignKeyStr = super.getAddForeignKeyConstraintString(constraintName, foreignKey,
                referencedTable, primaryKey, referencesPrimaryKey);

        return createForeignKeyStr;
    }
}
