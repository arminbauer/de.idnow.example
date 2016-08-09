# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                        bigint not null,
  name                      varchar(255),
  sla_time                  integer,
  sla_percentage            float,
  current_sla_percentage    float,
  constraint pk_company primary key (id))
;

create table identification (
  id                        bigint not null,
  name                      varchar(255),
  time                      bigint,
  waiting_time              integer,
  company_id                bigint,
  constraint pk_identification primary key (id))
;

create sequence company_seq;

create sequence identification_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists company;

drop table if exists identification;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists company_seq;

drop sequence if exists identification_seq;

