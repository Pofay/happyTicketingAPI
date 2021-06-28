CREATE TABLE USERS (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    oauthId VARCHAR(255) 
);

CREATE TABLE TASK(
    id VARCHAR(255),
    projectId BIGINT,
    taskName VARCHAR(255),
    taskStatus VARCHAR(255),
    estimatedTime INT 
);

CREATE TABLE PROJECT_MEMBER(
    userId BIGINT ,
    projectId BIGINT,
    memberRole VARCHAR(255),
    memberStatus VARCHAR(255) 
);

CREATE TABLE PROJECT(
    projectId SERIAL,
    projectName VARCHAR(255),
    channelId VARCHAR(255) 
);
