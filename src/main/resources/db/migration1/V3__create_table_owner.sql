CREATE TABLE IF NOT EXISTS owner (
    uid varchar(255) NOT NULL PRIMARY KEY,
    created_at timestamp(6) NULL,
    created_by_name varchar(255) NULL,
    created_by_uid varchar(255) NULL,
    deleted bool NULL,
    deleted_at timestamp(6) NULL,
    deleted_by_name varchar(255) NULL,
    deleted_by_uid varchar(255) NULL,
    updated_at timestamp(6) NULL,
    updated_by_name varchar(255) NULL,
    updated_by_uid varchar(255) NULL,
    account_id varchar(255) NOT NULL UNIQUE,
    FOREIGN KEY (account_id) REFERENCES account(uid)
);