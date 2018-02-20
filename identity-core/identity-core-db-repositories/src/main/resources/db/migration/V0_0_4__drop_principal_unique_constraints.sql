drop index uk_principal_type_name restrict;
create index idx_principal_type_name on principal (principal_type, name);