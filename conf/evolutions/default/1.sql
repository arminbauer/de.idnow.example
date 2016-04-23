# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                        integer not null,
  date_created              timestamp,
  date_updated              timestamp,
  name                      varchar(255),
  sla_time                  bigint,
  sla_percentage            double,
  current_sla_percentage    double,
  constraint pk_company primary key (id))
;

create table identification (
  id                        integer not null,
  date_created              timestamp,
  date_updated              timestamp,
  name                      varchar(255),
  time                      bigint,
  waiting_time              bigint,
  company_id                integer,
  constraint pk_identification primary key (id))
;

create sequence company_seq;

create sequence identification_seq;

alter table identification add constraint fk_identification_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_identification_company_1 on identification (company_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists company;

drop table if exists identification;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists company_seq;

drop sequence if exists identification_seq;

