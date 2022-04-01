create table member
(
    id   varchar(10) not null,
    name varchar(20) not null,
    primary key (id)
);

create table role
(
    member_id varchar(10) not null,
    role      varchar(10) not null,
    primary key (member_id),
    foreign key (member_id) references member (id)
);

create table users
(
    userId   varchar(12) not null,
    password varchar(12) not null,
    name     varchar(20) not null,
    email    varchar(50),
    primary key (userId)
);