# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table olz_action (
  id                        bigint not null,
  title                     varchar(255),
  done                      boolean,
  due_date                  timestamp,
  assigned_to_email         varchar(255),
  folder                    varchar(255),
  olz_list_id               bigint,
  constraint pk_olz_action primary key (id))
;

create table olz_list (
  id                        bigint not null,
  name                      varchar(255),
  folder                    varchar(255),
  constraint pk_olz_list primary key (id))
;

create table olz_user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_olz_user primary key (email))
;


create table olz_list_olz_user (
  olz_list_id                    bigint not null,
  olz_user_email                 varchar(255) not null,
  constraint pk_olz_list_olz_user primary key (olz_list_id, olz_user_email))
;
create sequence olz_action_seq;

create sequence olz_list_seq;

create sequence olz_user_seq;

alter table olz_action add constraint fk_olz_action_assignedTo_1 foreign key (assigned_to_email) references olz_user (email) on delete restrict on update restrict;
create index ix_olz_action_assignedTo_1 on olz_action (assigned_to_email);
alter table olz_action add constraint fk_olz_action_olzList_2 foreign key (olz_list_id) references olz_list (id) on delete restrict on update restrict;
create index ix_olz_action_olzList_2 on olz_action (olz_list_id);



alter table olz_list_olz_user add constraint fk_olz_list_olz_user_olz_list_01 foreign key (olz_list_id) references olz_list (id) on delete restrict on update restrict;

alter table olz_list_olz_user add constraint fk_olz_list_olz_user_olz_user_02 foreign key (olz_user_email) references olz_user (email) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists olz_action;

drop table if exists olz_list;

drop table if exists olz_list_olz_user;

drop table if exists olz_user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists olz_action_seq;

drop sequence if exists olz_list_seq;

drop sequence if exists olz_user_seq;

