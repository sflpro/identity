alter table principal add principal_status varchar(50) default 'MAIN' not null;
alter table principal alter column principal_status drop default;

alter table principal drop constraint uk_principal_name_type;
create unique index uk_principal_type_name on principal (principal_type, name);