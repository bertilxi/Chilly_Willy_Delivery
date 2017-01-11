create table location (
    id serial primary key,
    latitude real, 
    longitude real, 
    time_stamp integer not null
);

create table notification (
    id serial primary key,
    title varchar not null, 
    description varchar
);

create table flavor (
    id serial primary key,
    label varchar not null
);

create table review (
    id serial primary key,
    rating integer default 0 not null,
    img_url varchar,
    comment varchar
);

-- TODO: finish
create table order_flavor (
    id serial primary key,
);

/*
    containerType: string;
    containerSize: string;
    flavors: Array<string>;
    sauce: string;
    addins: Array<string>;
    quantity: number;
    delivered: boolean;
*/

create table container_type (
    id serial primary key,
    label varchar not null
);

create table container_size (
    id serial primary key,
    label varchar not null
);

create table sauce (
    id serial primary key,
    label varchar not null
);

-- TODO: finish
create table order_item (
    id serial primary key,
    container_type_id integer not null references container_type(id), 
    container_size_id integer not null references container_size(id),
    sauce_id integer not null references sauce(id),
    quantity integer default 1 not null,
    delivered boolean default false not null
);

create table order (
    id serial primary key,
); 
