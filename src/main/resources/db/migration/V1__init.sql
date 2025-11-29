-- src/main/resources/db/migration/V1__init.sql
CREATE TABLE documents (
id UUID PRIMARY KEY,
title TEXT NOT NULL,
owner_sub TEXT NOT NULL,
classification TEXT NOT NULL
);
INSERT INTO documents(id,title,owner_sub,classification) VALUES
('11111111-1111-1111-1111-111111111111','Project Plan','user-sub-1','internal'),
('22222222-2222-2222-2222-222222222222','Budget','user-sub-2','secret');