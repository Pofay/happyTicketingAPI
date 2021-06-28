CREATE TABLE USER (
    BIGINT id PRIMARY KEY SERIAL 
    VARCHAR(255) email,
    VARCHAR(255) oauthId
)

CREATE TABLE TASK(
    VARCHAR(255) id,
    BIGINT projectId,
    VARCHAR(255) taskName,
    VARCHAR(255) taskStatus,
    INT estimatedTime
)

CREATE TABLE PROJECT_MEMBER(
    BIGINT userId,
    BIGINT projectId,
    VARCHAR(255) memberRole,
    VARCHAR(255) memberStatus
)

CREATE TABLE PROJECT(
    BIGINT projectId,
    VARCHAR(255) projectName,
    VARCHAR(255) channelId
)

