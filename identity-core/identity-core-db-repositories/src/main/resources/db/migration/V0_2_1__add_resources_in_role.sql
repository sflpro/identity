create table role_resource (
  role_id bigint not null,
  resource_id bigint not null
);
alter table role_resource add constraint fk_role_resource_role foreign key (role_id) references role;
alter table role_resource add constraint fk_role_resource_resource foreign key (resource_id) references resource;