CREATE TABLE project_user(
    user_id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255),
    oauth_id VARCHAR(255)
);

CREATE TABLE project(
    project_id BIGSERIAL PRIMARY KEY,
    project_name VARCHAR(255),
    channel_id VARCHAR(255)
);

CREATE TABLE task(
    task_id VARCHAR(255) PRIMARY KEY,
    project_id BIGINT,
    task_name VARCHAR(255),
    task_status VARCHAR(255),
    assigned_to VARCHAR(255),
    estimated_time INT,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE project_member(
    user_user_id BIGINT,
    project_project_id BIGINT,
    member_role VARCHAR(255),
    member_status VARCHAR(255),
    CONSTRAINT fk_user FOREIGN KEY (user_user_id) REFERENCES project_user(user_id),
    CONSTRAINT fk_membership FOREIGN KEY (project_project_id) REFERENCES project(project_id),
    PRIMARY KEY(user_user_id, project_project_id)
);

CREATE SEQUENCE hibernate_sequence START 1;


