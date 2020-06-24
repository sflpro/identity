alter table token
    add column resource_id bigint null,
    add constraint fk_token_resource foreign key (resource_id) references resource;