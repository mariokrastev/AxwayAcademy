package com.axway.academy.addressbook.sql.schema;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.internal.util.StringHelper;

public class Oracle10gDialectAxwayImpl extends Oracle10gDialect {

    /*
     * The purpose of overriding this method is to automatically create index on foreign key column.
     * This way the MySql and Oracle schema will be consistent. 
     * 
     */
    @Override
    public String getAddForeignKeyConstraintString(String constraintName, String foreignKey[], String referencedTable, String primaryKey[], boolean referencesPrimaryKey) {
  
        String createForeignKeyStr = super.getAddForeignKeyConstraintString(constraintName, foreignKey,
                referencedTable, primaryKey, referencesPrimaryKey);
        
        String newline = System.getProperty("line.separator");
        
        StringBuilder createIndexBuilder = new StringBuilder(30);
        createIndexBuilder.append("create index ")
            .append(constraintName).append(" ON ")
            .append("(")
            .append(StringHelper.join(", ", foreignKey))
            .append(")");
         
        return createForeignKeyStr + ";" + newline + createIndexBuilder.toString();
    }
    
}
