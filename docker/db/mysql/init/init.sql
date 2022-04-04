create table member
(
    id   varchar(10) not null,
    name varchar(20) not null,
    primary key (id)
);

create table role(
    member_id varchar(10) not null,
    role varchar(10) not null,
    primary key (member_id),
    foreign key (member_id) references member (id)
);

create table player
(
    id   varchar(20) not null,
    primary key (id)
);

create table room
(
    id   varchar(20) not null,
    id_white_player   varchar(20) not null,
    id_black_player   varchar(20) not null,
    primary key (id)
);

create table board
(
    id int not null AUTO_INCREMENT,
    room_id   varchar(20) not null,
    position   varchar(20) not null,
    piece   varchar(20) not null,
    primary key (id),
    foreign key (room_id) references room (id)
);

create table turn
(
    id int not null AUTO_INCREMENT,
    room_id   varchar(20) not null,
    color   varchar(20) not null,
    primary key (id),
    foreign key (room_id) references room (id)
);

delete from board;
delete from turn;
delete from room;