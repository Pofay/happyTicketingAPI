
CREATE TABLE project_user(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    oauth_id VARCHAR(255)
);

CREATE TABLE task(
    task_id VARCHAR(255),
    project_id BIGINT,
    task_name VARCHAR(255),
    task_status VARCHAR(255),
    assigned_to VARCHAR(255),
    estimated_time INT
);

CREATE TABLE project_member(
    user_user_id BIGINT ,
    project_project_id BIGINT,
    member_role VARCHAR(255),
    member_status VARCHAR(255)
);

CREATE TABLE project(
    project_id SERIAL,
    project_name VARCHAR(255),
    channel_id VARCHAR(255)
);
