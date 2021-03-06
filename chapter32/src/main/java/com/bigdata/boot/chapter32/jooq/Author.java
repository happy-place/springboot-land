/*
 * This file is generated by jOOQ.
 */
package com.bigdata.boot.chapter32.jooq;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Author extends TableImpl<Record> {

    private static final long serialVersionUID = 2043258299;

    /**
     * The reference instance of <code>PUBLIC.AUTHOR</code>
     */
    public static final Author AUTHOR = new Author();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>PUBLIC.AUTHOR.ID</code>.
     */
    public final TableField<Record, Integer> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.AUTHOR.FIRST_NAME</code>.
     */
    public final TableField<Record, String> FIRST_NAME = createField(DSL.name("FIRST_NAME"), org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>PUBLIC.AUTHOR.LAST_NAME</code>.
     */
    public final TableField<Record, String> LAST_NAME = createField(DSL.name("LAST_NAME"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.AUTHOR.DATE_OF_BIRTH</code>.
     */
    public final TableField<Record, LocalDate> DATE_OF_BIRTH = createField(DSL.name("DATE_OF_BIRTH"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>PUBLIC.AUTHOR.YEAR_OF_BIRTH</code>.
     */
    public final TableField<Record, Integer> YEAR_OF_BIRTH = createField(DSL.name("YEAR_OF_BIRTH"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>PUBLIC.AUTHOR.DISTINGUISHED</code>.
     */
    public final TableField<Record, Byte> DISTINGUISHED = createField(DSL.name("DISTINGUISHED"), org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * Create a <code>PUBLIC.AUTHOR</code> table reference
     */
    public Author() {
        this(DSL.name("AUTHOR"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.AUTHOR</code> table reference
     */
    public Author(String alias) {
        this(DSL.name(alias), AUTHOR);
    }

    /**
     * Create an aliased <code>PUBLIC.AUTHOR</code> table reference
     */
    public Author(Name alias) {
        this(alias, AUTHOR);
    }

    private Author(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Author(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Author(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, AUTHOR);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.CONSTRAINT_7;
    }

    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.CONSTRAINT_7);
    }

    @Override
    public Author as(String alias) {
        return new Author(DSL.name(alias), this);
    }

    @Override
    public Author as(Name alias) {
        return new Author(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Author rename(String name) {
        return new Author(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Author rename(Name name) {
        return new Author(name, null);
    }
}
