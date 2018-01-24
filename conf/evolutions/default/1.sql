# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                            bigint not null,
  company_name                  varchar(255),
  sla_time                      bigint,
  sla_percentage                float,
  current_sla_percentage        float,
  constraint pk_company primary key (id)
);
create sequence company_seq;

create table identification (
  id                            bigint not null,
  name                          varchar(255),
  start_timestamp               bigint,
  waiting_time                  bigint,
  company_id                    bigint,
  constraint pk_identification primary key (id)
);
create sequence identification_seq;

alter table identification add constraint fk_identification_company_id foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_identification_company_id on identification (company_id);


# --- !Downs

alter table identification drop constraint if exists fk_identification_company_id;
drop index if exists ix_identification_company_id;

drop table if exists company;
drop sequence if exists company_seq;

drop table if exists identification;
drop sequence if exists identification_seq;

