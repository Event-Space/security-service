ALTER TABLE user_unit.users
    DROP COLUMN username;

ALTER TABLE user_unit.users
    ALTER COLUMN first_name DROP NOT NULL;

ALTER TABLE user_unit.users
    ALTER COLUMN phone_number DROP NOT NULL;