
    drop table test cascade constraints;

    drop sequence hibernate_sequence;

    create table test (
        id number(19,0) not null,
        version number(19,0) not null,
        pippo boolean not null,
        primary key (id)
    );

    create sequence hibernate_sequence;
