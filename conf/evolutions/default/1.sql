# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                            varchar(255) not null,
  name                          varchar(255),
  sla_time_seconds              integer,
  sla_percent                   double,
  current_sla_percent           double,
  constraint pk_company primary key (id)
);

create table identification (
  id                            varchar(255) not null,
  name_of_user                  varchar(255),
  start_time                    bigint,
  waiting_time                  bigint,
  company_id                    varchar(255),
  constraint pk_identification primary key (id)
);

alter table identification add constraint fk_identification_company_id foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_identification_company_id on identification (company_id);


# --- !Downs

alter table identification drop constraint if exists fk_identification_company_id;
drop index if exists ix_identification_company_id;

drop table if exists company;

drop table if exists identification;

