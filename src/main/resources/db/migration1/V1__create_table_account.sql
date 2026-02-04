CREATE TABLE public.account (
                                uid varchar(255) NOT NULL,
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
                                email varchar(255) NULL,
                                "name" varchar(255) NULL,
                                "password" varchar(255) NULL,
                                phone varchar(255) NULL,
                                "role" varchar(255) NULL,
                                tax_number varchar(255) NULL,
                                role_account_enum varchar(255) NULL,
                                closing_hours time(0) NULL,
                                contact varchar(255) NULL,
                                opening_hours time(0) NULL,
                                address_uid varchar(255) NULL,
                                owner_uid varchar(255) NULL,
                                CONSTRAINT account_pkey PRIMARY KEY (uid),
                                CONSTRAINT account_role_account_enum_check CHECK (((role_account_enum)::text = ANY ((ARRAY['ROLE_USER'::character varying, 'ROLE_ADM'::character varying, 'ROLE_OWNER'::character varying, 'ROLE_BARBER'::character varying])::text[]))),
	CONSTRAINT account_role_check CHECK (((role)::text = ANY ((ARRAY['ROLE_USER'::character varying, 'ROLE_ADM'::character varying, 'ROLE_OWNER'::character varying, 'ROLE_BARBER'::character varying])::text[]))),
	CONSTRAINT uk77qwsmy0k12n2520i49tgw3yv UNIQUE (address_uid)
);


-- public.account chaves estrangeiras

ALTER TABLE public.account ADD CONSTRAINT fk3cmjkjplsflcph3dgshgn033b FOREIGN KEY (owner_uid) REFERENCES public."owner"(uid);
ALTER TABLE public.account ADD CONSTRAINT fkd11cqownh9cur97p0jycpykr8 FOREIGN KEY (address_uid) REFERENCES public.address(uid);