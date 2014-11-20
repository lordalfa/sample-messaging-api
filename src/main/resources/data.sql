insert into users (username, password, api_token) values ('test1', 'password', '00294af8-6dc8-41cf-9e15-3965c4c012fc');
insert into users (username, password, api_token) values ('test2', 'password', 'ab68ec01-534f-48a1-903f-571b539e139b');
insert into users (username, password, api_token) values ('test3', 'password', 'f00f7f29-f2bb-4e7e-bebe-60d31134589e');
insert into users (username, password, api_token) values ('test4', 'password', '6cbf0b47-b9d7-44bc-a0dc-f654a9495878');

insert into followers (username, following) values('test1', 'test2');
insert into followers (username, following) values('test1', 'test3');
insert into followers (username, following) values('test1', 'test4');
insert into followers (username, following) values('test2', 'test1');
insert into followers (username, following) values('test3', 'test4');

insert into messages (author, text, creation_time) values ('test1', 'A sample 160- characters message from test1', 0);
insert into messages (author, text, creation_time) values ('test2', 'A sample 160- characters message from test2', 0);
insert into messages (author, text, creation_time) values ('test3', 'A sample 160- characters message from test3', 0);
insert into messages (author, text, creation_time) values ('test4', 'A sample 160- characters message from test4', 0);