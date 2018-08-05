# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                            bigint not null,
  name                          varchar(255) not null,
  sla_time_in_seconds           integer not null,
  sla_percentage                float not null,
  current_sla_percentage        float not null,
  is_deleted                    boolean,
  constraint pk_company primary key (id)
);
create sequence COMPANY_seq;

create table identification (
  id                            bigint not null,
  username                      varchar(255) not null,
  started_at                    timestamp not null,
  company_id                    bigint not null,
  is_pending                    boolean not null,
  is_deleted                    boolean not null,
  constraint pk_identification primary key (id)
);
create sequence IDENTIFICATION_seq;

create index ix_company_is_deleted on COMPANY (is_deleted);
create index ix_identification_is_pending on IDENTIFICATION (is_pending);
create index ix_identification_is_deleted on IDENTIFICATION (is_deleted);
alter table identification add constraint fk_identification_company_id foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_identification_company_id on identification (company_id);


# --- !Downs

alter table identification drop constraint if exists fk_identification_company_id;
drop index if exists ix_identification_company_id;

drop table if exists company;
drop sequence if exists COMPANY_seq;

drop table if exists identification;
drop sequence if exists IDENTIFICATION_seq;

drop index if exists ix_company_is_deleted;
drop index if exists ix_identification_is_pending;
drop index if exists ix_identification_is_deleted;
