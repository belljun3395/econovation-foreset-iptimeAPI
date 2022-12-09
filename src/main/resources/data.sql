drop table if exists club_arrival_departure_list CASCADE;
drop table if exists member_mac_address_list CASCADE;

create table club_arrival_departure_list
(
    id         bigint generated by default as identity,
    local_date date,
    member_id  bigint,
    primary key (id)
);

create table member_mac_address_list
(
    id          bigint generated by default as identity,
    mac_address varchar(255),
    member_id   bigint,
    primary key (id)
);

insert
into member_mac_address_list
    (id, mac_address, member_id)
values (default, '90:32:4B:18:00:1B', 1);

insert
into member_mac_address_list
(id, mac_address, member_id)
values (default, 'F4:D4:88:75:85:1A', 2);

insert
into member_mac_address_list
(id, mac_address, member_id)
values (default, '1A:E6:56:14:1B:F5', 3);

insert
into member_mac_address_list
(id, mac_address, member_id)
values (default, 'DE:CF:44:B1:A4:2E', 4);
