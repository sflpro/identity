create sequence seq_identity_resource_role start 1 increment 50;

alter table identity_role
    rename to identity_resource_role;

alter table identity_resource_role
    add column id          bigint    not null default nextval('seq_identity_resource_role'),
    add column created     timestamp not null default CURRENT_TIMESTAMP,
    add column updated     timestamp,
    add column deleted     timestamp,
    add column resource_id bigint    null,
    add constraint pk_identity_resource_role primary key (id),
    add constraint fk_identity_resource_role foreign key (resource_id) references resource,
    add constraint uk_identity_role unique (identity_id, role_id),
    add constraint uk_resource_role unique (resource_id, role_id);