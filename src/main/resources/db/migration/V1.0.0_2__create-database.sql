CREATE TABLE user_service."user" (
   tid           int8 NOT NULL,
   id            uuid NOT NULL,
   email         varchar (255) NOT NULL,
   name          varchar (255) NOT NULL,
   password      varchar (255) NOT NULL,
   role          varchar (127) NOT NULL,
   tenant_id     uuid NOT NULL,
   PRIMARY KEY (tid)
);

CREATE TABLE user_service.user_group (
   tid         int8 NOT NULL,
   id          uuid NOT NULL,
   name        varchar (127) NOT NULL,
   tenant_id   uuid NOT NULL,
   PRIMARY KEY (tid)
);

CREATE TABLE user_service.user_group_users (
   user_groups_tid   int8 NOT NULL,
   users_tid         int8 NOT NULL,
   PRIMARY KEY (user_groups_tid, users_tid)
);

ALTER TABLE user_service."user"
   ADD CONSTRAINT unique_user_id UNIQUE (id);

ALTER TABLE user_service."user"
   ADD CONSTRAINT unique_user_tenant_name UNIQUE (tenant_id, email);

ALTER TABLE user_service.user_group
   ADD CONSTRAINT unique_user_group_tenant_name UNIQUE (tenant_id, name);

ALTER TABLE user_service.user_group
   ADD CONSTRAINT unique_user_group_id UNIQUE (id);

ALTER TABLE user_service.user_group_users
   ADD CONSTRAINT fk_group_users_users FOREIGN KEY (users_tid)
       REFERENCES user_service."user";

ALTER TABLE user_service.user_group_users
   ADD CONSTRAINT fk_group_users_user_groups FOREIGN KEY (user_groups_tid)
       REFERENCES user_service.user_group;

CREATE UNIQUE INDEX user_email_case_insensitive_unique
   ON user_service."user" (lower (email));
