alter table identity_role add column resource_id bigint null;
alter table identity_role add constraint fk_identity_role_resource foreign key (resource_id) references resource;