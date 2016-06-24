package com.excilys.core.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QComputer is a Querydsl query type for Computer
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QComputer extends EntityPathBase<Computer> {

    private static final long serialVersionUID = 670752677;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QComputer computer = new QComputer("computer");

    protected QCompany company;

    public final ComparablePath<java.time.LocalDate> discontinued = createComparable("discontinued", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.time.LocalDate> introduced = createComparable("introduced", java.time.LocalDate.class);

    public final StringPath name = createString("name");

    public QComputer(String variable) {
        this(Computer.class, forVariable(variable), INITS);
    }

    public QComputer(Path<? extends Computer> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QComputer(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QComputer(PathMetadata<?> metadata, PathInits inits) {
        this(Computer.class, metadata, inits);
    }

    public QComputer(Class<? extends Computer> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

    public QCompany company() {
        if (company == null){
            company = new QCompany(forProperty("company"));
        }
        return company;
    }

}

