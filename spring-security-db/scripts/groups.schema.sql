CREATE TABLE groups (
  id BIGSERIAL NOT NULL PRIMARY KEY ,
  group_name VARCHAR(50) not null
);

CREATE TABLE group_authorities (
  group_id BIGINT NOT NULL,
  authority VARCHAR(50) not null,
  CONSTRAINT fk_group_authorities_group FOREIGN KEY(group_id) REFERENCES groups(id)
);

CREATE TABLE group_members (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  group_id BIGINT NOT NULL,
  CONSTRAINT fk_group_members_group FOREIGN KEY(group_id) REFERENCES groups(id)
);