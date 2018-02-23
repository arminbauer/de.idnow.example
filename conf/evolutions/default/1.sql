# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                            integer not null,
  name                          varchar(255),
  sla_time                      integer,
  sla_percentage                float,
  current_sla_percentage        float,
  constraint pk_company primary key (id)
);
create sequence company_seq;

create table identification (
  id                            integer not null,
  name                          varchar(255),
  time                          bigint,
  waiting_time                  integer,
  companyid                     integer,
  constraint pk_identification primary key (id)
);
create sequence identification_seq;


# --- !Downs

drop table if exists company;
drop sequence if exists company_seq;

drop table if exists identification;
drop sequence if exists identification_seq;

