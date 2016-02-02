# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                        bigint not null,
  name                      varchar(255) not null,
  sla_time                  integer not null,
  sla_percentage            float not null,
  current_sla_percentage    float not null,
  constraint pk_company primary key (id))
;

create sequence company_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists company;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists company_seq;

