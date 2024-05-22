
-- CREATE DATABASE  attendance;
-- CREATE USER attendance_users WITH PASSWORD '12345';
CREATE SCHEMA IF NOT EXISTS attendance;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA attendance TO attendance_users;
GRANT ALL PRIVILEGES ON DATABASE attendance TO attendance_users;
GRANT ALL PRIVILEGES ON SCHEMA attendance TO attendance_users;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA attendance to attendance_users;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA attendance to attendance_users;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA attendance to attendance_users;

CREATE TABLE IF NOT EXISTS attendance.users(
    id          varchar(100)  not null primary key,
    firstname   varchar(100) not null,
    lastname    varchar(100) not null,
    middlename  varchar(100),
    email       varchar(45)   unique,
    phone       varchar(15)  not null unique,
    role   varchar(15),
    employmentType   varchar(100),
    department   varchar(100),
    designation   varchar(100),
    joinDate Date,
    client   varchar(100),
    password    varchar(150) not null,
    enabled     boolean default true,
    created_by   varchar(100),
    created_at   timestamp without time zone DEFAULT timezone('Africa/Lagos'::text, CURRENT_TIMESTAMP(0)),
    modified_by  varchar(100),
    modified_at  timestamp without time zone,
    default_password boolean default true
    );


CREATE TABLE IF NOT EXISTS attendance.attendance_record(
    id  varchar(100)  not null primary key,
    user_id varchar(100) not null, -- Foreign key referencing users.id
    clockInTime timestamp without time zone,
    clockOutTime timestamp without time zone,
    foreign key (user_id) references attendance.users(id) -- Establishes relationship with users table

);


CREATE TABLE IF NOT EXISTS attendance.leave (
    id varchar(100) not null primary key,
    user_id varchar(100) not null, -- Foreign key referencing users.id
    startDate timestamp without time zone,
    endDate timestamp without time zone,
    appliedDate timestamp without time zone,
    reason varchar(100),
    status varchar(15),
    created_at timestamp without time zone,
    modified_by varchar(100),
    modified_at timestamp without time zone
    );





CREATE TABLE IF NOT EXISTS attendance.access_token
(
    id            serial primary key,
    token         text not null,
    refresh_token text ,
    user_id       character varying(50) NOT NULL,
    expired       boolean default false,
    revoked       boolean default false,
    logout        boolean default false,
    logout_date   timestamp without time zone,
    entry_date    timestamp without time zone DEFAULT timezone('Africa/Lagos'::text, CURRENT_TIMESTAMP(0)),
    expiry_date   timestamp without time zone
    );

create table if not exists attendance.client
(
    id varchar(100)  not null primary key,
    name    varchar(50),
    address    varchar(150),
    created_by   varchar(100),
    created_at   timestamp without time zone DEFAULT timezone('Africa/Lagos'::text, CURRENT_TIMESTAMP(0)),
    modified_by  varchar(100),
    modified_at  timestamp without time zone
    );
