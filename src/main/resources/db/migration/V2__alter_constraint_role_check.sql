ALTER TABLE account DROP CONSTRAINT account_role_check;

ALTER TABLE account ADD CONSTRAINT account_role_check CHECK ( role IN ('ROLE_USER', 'ROLE_ADM', 'ROLE_OWNER', 'ROLE_BARBER'));