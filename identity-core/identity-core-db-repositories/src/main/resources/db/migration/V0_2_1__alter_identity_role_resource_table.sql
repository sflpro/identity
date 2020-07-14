create sequence seq_identity_resource_role start 1 increment 50;

alter table identity_role
    rename to identity_resource_role;

alter table identity_resource_role
    add column id          bigint    not null default nextval('seq_identity_resource_role'),
    add column resource_id bigint    null,
    add column created     timestamp not null default CURRENT_TIMESTAMP,
    add column updated     timestamp,
    add column deleted     timestamp,
    add constraint pk_identity_resource_role primary key (id),
    add constraint fk_identity_resource_role foreign key (resource_id) references resource;

create unique index uk_identity_role on identity_resource_role (identity_id, role_id) where resource_id is null and deleted is null;
create unique index uk_identity_role_deleted on identity_resource_role (identity_id, role_id, deleted) where resource_id is null;
create unique index uk_identity_resource_role on identity_resource_role (identity_id, resource_id, role_id) where deleted is null;
create unique index uk_identity_resource_role_deleted on identity_resource_role (identity_id, resource_id, role_id, deleted);