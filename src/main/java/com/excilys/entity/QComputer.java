package com.excilys.entity;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.ComparablePath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;


/**
 * QComputer is a Querydsl query type for Computer
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QComputer extends EntityPathBase<Computer> {

    private static final long serialVersionUID = 1567629592;

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

