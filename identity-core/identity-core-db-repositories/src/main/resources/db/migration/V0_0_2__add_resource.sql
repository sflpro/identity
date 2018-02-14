create sequence seq_resource start 1 increment 50;

create table resource (
  id bigint not null constraint pk_resource primary key,
  type varchar(50) not null,
  identifier character(36) not null,
  created timestamp not null,
  updated timestamp not null,
  deleted timestamp,
  constraint uk_resource_type_identifier unique (type, identifier, deleted)
);
create table identity_resource (
  identity_id character(36) not null,
  resource_id bigint not null
);
alter table identity_resource add constraint fk_identity_resource_identity foreign key (identity_id) references identity;
alter table identity_resource add constraint fk_identity_resource_resource_id foreign key (resource_id) references resource;