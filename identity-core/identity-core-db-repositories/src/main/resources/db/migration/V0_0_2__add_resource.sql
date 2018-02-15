create sequence seq_resource start 1 increment 50;
create sequence seq_identity_resource start 1 increment 50;

create table resource (
  id bigint not null constraint pk_resource primary key,
  type varchar(50) not null,
  identifier varchar(50) not null,
  created timestamp not null,
  updated timestamp not null,
  deleted timestamp,
  constraint uk_resource_type_identifier unique (type, identifier, deleted)
);
create unique index if not exists uk_resource_type_identifier_on_null_deleted on resource (type, identifier) where deleted is null;

create table identity_resource (
  id bigint not null constraint pk_identity_resource primary key,
  identity_id character(36) not null,
  resource_id bigint not null,
  constraint uk_identity_resource unique (identity_id, resource_id)
);
alter table identity_resource add constraint fk_identity_resource_identity foreign key (identity_id) references identity;
alter table identity_resource add constraint fk_identity_resource_resource_id foreign key (resource_id) references resource;