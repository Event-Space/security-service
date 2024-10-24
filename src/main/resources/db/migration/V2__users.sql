ALTER TABLE user_unit.users
    ALTER COLUMN first_name DROP NOT NULL;

ALTER TABLE user_unit.users
    ALTER COLUMN phone_number DROP NOT NULL;

ALTER TABLE user_unit.users
    ALTER COLUMN username DROP NOT NULL;