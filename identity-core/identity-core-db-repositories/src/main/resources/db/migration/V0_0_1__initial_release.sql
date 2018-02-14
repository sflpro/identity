create sequence seq_permission start 1 increment 50;
create sequence seq_role start 1 increment 50;

create table identity (
  id character(36) not null constraint pk_identity primary key,
  description varchar(255) not null,
  secret varchar(100),
  contact_method varchar(50),
  status varchar(50),
  created timestamp not null,
  updated timestamp not null,
  deleted timestamp
);

create table credential (
  id character(36) not null constraint pk_credential primary key,
  identity_id character(36),
  type varchar(50),
  failed_attempts integer,
  details varchar(300),
  created timestamp not null,
  updated timestamp not null,
  deleted timestamp
);
alter table credential add constraint fk_credential_identity foreign key (identity_id) references identity;

create table authentication_attempt (
  id character(36) not null constraint pk_authentication_attempt primary key,
  credential_id character(36),
  result varchar(50),
  details varchar(300),
  created timestamp not null,
  updated timestamp not null,
  deleted timestamp
);
alter table authentication_attempt add constraint fk_auth_attempt_credential_id foreign key(credential_id) references credential;

create table role (
  id bigint not null constraint pk_role primary key,
  name varchar(50) not null,
  created timestamp not null,
  updated timestamp not null,
  deleted timestamp,
  constraint uk_role_name unique (name, deleted)
);

create table permission (
  id bigint not null constraint pk_permission primary key,
  name varchar(100) not null,
  type varchar(50) not null,
  created timestamp not null,
  updated timestamp,
  deleted timestamp,
  constraint uk_permission_name unique (name, deleted)
);

create table role_permission (
  role_id bigint not null,
  permission_id bigint not null
);
alter table role_permission add constraint fk_role_permission_role foreign key (role_id) references role;
alter table role_permission add constraint fk_role_permission_permission foreign key (permission_id) references permission;

create table identity_role (
  identity_id character(36) not null,
  role_id bigint not null
);
alter table identity_role add constraint fk_identity_role_identity foreign key (identity_id) references identity;
alter table identity_role add constraint fk_identity_role_role foreign key (role_id) references role;

create table principal (
  credential_id character(36) not null constraint pk_principal primary key,
  name varchar(100),
  principal_type varchar(50),
  constraint uk_principal_name_type unique (name, principal_type)
);
alter table principal add constraint fk_principal_credential foreign key (credential_id) references credential;

create table token (
  credential_id character(36) not null,
  value varchar(300),
  token_type varchar(50),
  expiration_date timestamp,
  issued_by character(36),
  constraint uk_token_type unique (value, token_type)
);
alter table token add constraint fk_token_credential foreign key (credential_id) references credential;
alter table token add constraint fk_token_issuedBy_credential foreign key (issued_by) references credential;