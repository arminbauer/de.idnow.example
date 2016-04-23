# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                        integer auto_increment not null,
  date_created              datetime(6),
  date_updated              datetime(6),
  name                      varchar(255),
  sla_time                  bigint,
  sla_percentage            double,
  current_sla_percentage    double,
  constraint pk_company primary key (id))
;

create table identification (
  id                        integer auto_increment not null,
  date_created              datetime(6),
  date_updated              datetime(6),
  name                      varchar(255),
  time                      bigint,
  waiting_time              bigint,
  company_id                integer,
  constraint pk_identification primary key (id))
;

alter table identification add constraint fk_identification_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_identification_company_1 on identification (company_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table company;

drop table identification;

SET FOREIGN_KEY_CHECKS=1;

