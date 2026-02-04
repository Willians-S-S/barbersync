CREATE TABLE public.account (
                                uid character varying(255) NOT NULL,
                                created_at timestamp(6) without time zone,
                                created_by_name character varying(255),
                                created_by_uid character varying(255),
                                deleted boolean,
                                deleted_at timestamp(6) without time zone,
                                deleted_by_name character varying(255),
                                deleted_by_uid character varying(255),
                                updated_at timestamp(6) without time zone,
                                updated_by_name character varying(255),
                                updated_by_uid character varying(255),
                                email character varying(255),
                                name character varying(255),
                                password character varying(255),
                                phone character varying(255),
                                role character varying(255),
                                tax_number character varying(255),
                                role_account_enum character varying(255),
                                closing_hours time(0) without time zone,
                                contact character varying(255),
                                opening_hours time(0) without time zone,
                                address_uid character varying(255),
                                owner_uid character varying(255),
                                CONSTRAINT account_role_account_enum_check CHECK (((role_account_enum)::text = ANY ((ARRAY['ROLE_USER'::character varying, 'ROLE_ADM'::character varying, 'ROLE_OWNER'::character varying, 'ROLE_BARBER'::character varying])::text[]))),
    CONSTRAINT account_role_check CHECK (((role)::text = ANY ((ARRAY['ROLE_USER'::character varying, 'ROLE_ADM'::character varying, 'ROLE_OWNER'::character varying, 'ROLE_BARBER'::character varying])::text[])))
);


ALTER TABLE public.account OWNER TO postgres;


CREATE TABLE public.account_employee (
                                         barbershop_uid character varying(255) NOT NULL,
                                         employee_uid character varying(255) NOT NULL
);


ALTER TABLE public.account_employee OWNER TO postgres;


CREATE TABLE public.address (
                                uid character varying(255) NOT NULL,
                                created_at timestamp(6) without time zone,
                                created_by_name character varying(255),
                                created_by_uid character varying(255),
                                deleted boolean,
                                deleted_at timestamp(6) without time zone,
                                deleted_by_name character varying(255),
                                deleted_by_uid character varying(255),
                                updated_at timestamp(6) without time zone,
                                updated_by_name character varying(255),
                                updated_by_uid character varying(255),
                                street character varying(255) NOT NULL,
                                number character varying(255),
                                neighborhood character varying(255) NOT NULL,
                                city character varying(255) NOT NULL,
                                zip_code character varying(255) NOT NULL
);


ALTER TABLE public.address OWNER TO postgres;


CREATE TABLE public.appointments (
                                     uid character varying(255) NOT NULL,
                                     created_at timestamp(6) without time zone,
                                     created_by_name character varying(255),
                                     created_by_uid character varying(255),
                                     deleted boolean,
                                     deleted_at timestamp(6) without time zone,
                                     deleted_by_name character varying(255),
                                     deleted_by_uid character varying(255),
                                     updated_at timestamp(6) without time zone,
                                     updated_by_name character varying(255),
                                     updated_by_uid character varying(255),
                                     appointments_status character varying(255),
                                     scheduled_at timestamp(6) without time zone NOT NULL,
                                     total_value numeric(38,2),
                                     client_uid character varying(255) NOT NULL,
                                     employee_uid character varying(255) NOT NULL,
                                     CONSTRAINT appointments_appointments_status_check CHECK (((appointments_status)::text = ANY ((ARRAY['SCHEDULED'::character varying, 'CANCELED'::character varying, 'UNATTENDED'::character varying, 'COMPLETED'::character varying])::text[])))
);


ALTER TABLE public.appointments OWNER TO postgres;


CREATE TABLE public.barbershop (
                                   uid character varying(255) NOT NULL,
                                   created_at timestamp(6) without time zone,
                                   created_by_name character varying(255),
                                   created_by_uid character varying(255),
                                   deleted boolean,
                                   deleted_at timestamp(6) without time zone,
                                   deleted_by_name character varying(255),
                                   deleted_by_uid character varying(255),
                                   updated_at timestamp(6) without time zone,
                                   updated_by_name character varying(255),
                                   updated_by_uid character varying(255),
                                   name character varying(255) NOT NULL,
                                   tax_number character varying(255) NOT NULL,
                                   openig_hours timestamp(6) without time zone NOT NULL,
                                   closing_hours timestamp(6) without time zone NOT NULL,
                                   owner_id character varying(255) NOT NULL,
                                   address_id character varying(255)
);


ALTER TABLE public.barbershop OWNER TO postgres;


CREATE TABLE public.client (
                               uid character varying(255) NOT NULL,
                               created_at timestamp(6) without time zone,
                               created_by_name character varying(255),
                               created_by_uid character varying(255),
                               deleted boolean,
                               deleted_at timestamp(6) without time zone,
                               deleted_by_name character varying(255),
                               deleted_by_uid character varying(255),
                               updated_at timestamp(6) without time zone,
                               updated_by_name character varying(255),
                               updated_by_uid character varying(255),
                               account_id character varying(255) NOT NULL,
                               account_uid character varying(255)
);


ALTER TABLE public.client OWNER TO postgres;


CREATE TABLE public.client_appointments (
                                            client_uid character varying(255) NOT NULL,
                                            appointments_uid character varying(255) NOT NULL
);


ALTER TABLE public.client_appointments OWNER TO postgres;


CREATE TABLE public.employee (
                                 uid character varying(255) NOT NULL,
                                 created_at timestamp(6) without time zone,
                                 created_by_name character varying(255),
                                 created_by_uid character varying(255),
                                 deleted boolean,
                                 deleted_at timestamp(6) without time zone,
                                 deleted_by_name character varying(255),
                                 deleted_by_uid character varying(255),
                                 updated_at timestamp(6) without time zone,
                                 updated_by_name character varying(255),
                                 updated_by_uid character varying(255),
                                 commision_rate numeric(10,2),
                                 start_work_schedule timestamp(6) without time zone NOT NULL,
                                 end_work_schedule timestamp(6) without time zone NOT NULL,
                                 role_employee character varying(255) NOT NULL,
                                 account_id character varying(255) NOT NULL,
                                 barbershop_id character varying(255) NOT NULL,
                                 services_id character varying(255),
                                 commission_rate numeric(38,2),
                                 end_time time(0) without time zone,
                                 start_time time(0) without time zone,
                                 account_uid character varying(255),
                                 barbershop_uid character varying(255),
                                 CONSTRAINT role_employee_enum CHECK (((role_employee)::text = ANY ((ARRAY['BARBER'::character varying, 'RECEPTIONIST'::character varying])::text[])))
);


ALTER TABLE public.employee OWNER TO postgres;


CREATE TABLE public.employee_appointments (
                                              employee_uid character varying(255) NOT NULL,
                                              appointments_uid character varying(255) NOT NULL
);


ALTER TABLE public.employee_appointments OWNER TO postgres;


CREATE TABLE public.employee_service (
                                         employees_uid character varying(255) NOT NULL,
                                         services_uid character varying(255) NOT NULL
);


ALTER TABLE public.employee_service OWNER TO postgres;



CREATE TABLE public.owner (
                              uid character varying(255) NOT NULL,
                              created_at timestamp(6) without time zone,
                              created_by_name character varying(255),
                              created_by_uid character varying(255),
                              deleted boolean,
                              deleted_at timestamp(6) without time zone,
                              deleted_by_name character varying(255),
                              deleted_by_uid character varying(255),
                              updated_at timestamp(6) without time zone,
                              updated_by_name character varying(255),
                              updated_by_uid character varying(255),
                              account_id character varying(255) NOT NULL,
                              account_uid character varying(255)
);


ALTER TABLE public.owner OWNER TO postgres;


CREATE TABLE public.owner_barbershops (
                                          owner_uid character varying(255) NOT NULL,
                                          barbershops_uid character varying(255) NOT NULL
);


ALTER TABLE public.owner_barbershops OWNER TO postgres;


CREATE TABLE public.services (
                                 uid character varying(255) NOT NULL,
                                 created_at timestamp(6) without time zone,
                                 created_by_name character varying(255),
                                 created_by_uid character varying(255),
                                 deleted boolean,
                                 deleted_at timestamp(6) without time zone,
                                 deleted_by_name character varying(255),
                                 deleted_by_uid character varying(255),
                                 updated_at timestamp(6) without time zone,
                                 updated_by_name character varying(255),
                                 updated_by_uid character varying(255),
                                 name character varying(255) NOT NULL,
                                 description character varying(255),
                                 price numeric(38,2),
                                 duration_minutes integer,
                                 category character varying(255),
                                 duration character varying(255) NOT NULL,
                                 services_uid character varying(255) NOT NULL
);


ALTER TABLE public.services OWNER TO postgres;


ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.barbershop
    ADD CONSTRAINT barbershop_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_account_id_key UNIQUE (account_id);



ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT fk_unique UNIQUE (services_id);


ALTER TABLE ONLY public.owner
    ADD CONSTRAINT owner_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (uid);



ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk4u3eoyn5496o34lqi7erl6nxf UNIQUE (account_uid);



ALTER TABLE ONLY public.account
    ADD CONSTRAINT uk77qwsmy0k12n2520i49tgw3yv UNIQUE (address_uid);



ALTER TABLE ONLY public.owner
    ADD CONSTRAINT ukbx040rl5l6pv64tu4celf1mlq UNIQUE (account_uid);



ALTER TABLE ONLY public.owner_barbershops
    ADD CONSTRAINT ukcanapeg3wp5gu9snjv04htbu7 UNIQUE (barbershops_uid);



ALTER TABLE ONLY public.employee_appointments
    ADD CONSTRAINT uklwh5ywy1poxt75snd2o6mbdf1 UNIQUE (appointments_uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT uknxa05xj3nnbtrbnhubealyicy UNIQUE (account_uid);



ALTER TABLE ONLY public.account_employee
    ADD CONSTRAINT ukqov7g572po1cwd27jtlsrftyg UNIQUE (employee_uid);



ALTER TABLE ONLY public.client_appointments
    ADD CONSTRAINT uktb88p67riwopc6wwprg61od5o UNIQUE (appointments_uid);



ALTER TABLE ONLY public.barbershop
    ADD CONSTRAINT uq_barbershop_address UNIQUE (address_id);



ALTER TABLE ONLY public.barbershop
    ADD CONSTRAINT barbershop_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES public.owner(uid);



ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_barbershop_id_fkey FOREIGN KEY (barbershop_id) REFERENCES public.barbershop(uid);



ALTER TABLE ONLY public.account
    ADD CONSTRAINT fk3cmjkjplsflcph3dgshgn033b FOREIGN KEY (owner_uid) REFERENCES public.owner(uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT fk71d5rvo9r4dtq3b3adtt7brsv FOREIGN KEY (barbershop_uid) REFERENCES public.account(uid);



ALTER TABLE ONLY public.owner_barbershops
    ADD CONSTRAINT fk7fqjonrl6k23aokpn0uy6leux FOREIGN KEY (owner_uid) REFERENCES public.owner(uid);



ALTER TABLE ONLY public.owner_barbershops
    ADD CONSTRAINT fk8b672fsd0540tfvj1ig4ms5ke FOREIGN KEY (barbershops_uid) REFERENCES public.account(uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT fk8d4becdm52sn8p8tcv99cxhs9 FOREIGN KEY (account_uid) REFERENCES public.account(uid);



ALTER TABLE ONLY public.client_appointments
    ADD CONSTRAINT fk9sypygnyvwx8osume1aveqlao FOREIGN KEY (appointments_uid) REFERENCES public.appointments(uid);



ALTER TABLE ONLY public.barbershop
    ADD CONSTRAINT fk_barber_addres FOREIGN KEY (address_id) REFERENCES public.address(uid);



ALTER TABLE ONLY public.employee
    ADD CONSTRAINT fk_service FOREIGN KEY (services_id) REFERENCES public.services(uid);



ALTER TABLE ONLY public.account_employee
    ADD CONSTRAINT fka55rhx93ygwp6ybovec8h17pq FOREIGN KEY (employee_uid) REFERENCES public.employee(uid);



ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fkcm7b7a990blw4gsqdrsdlifbj FOREIGN KEY (client_uid) REFERENCES public.client(uid);



ALTER TABLE ONLY public.account
    ADD CONSTRAINT fkd11cqownh9cur97p0jycpykr8 FOREIGN KEY (address_uid) REFERENCES public.address(uid);



ALTER TABLE ONLY public.services
    ADD CONSTRAINT fkd88euqpwa3bi5celxfiiqrd36 FOREIGN KEY (services_uid) REFERENCES public.appointments(uid);



ALTER TABLE ONLY public.account_employee
    ADD CONSTRAINT fkemxq3bwhe2uprsgu0nj3oopks FOREIGN KEY (barbershop_uid) REFERENCES public.account(uid);



ALTER TABLE ONLY public.employee_appointments
    ADD CONSTRAINT fkep6dts85j338fbgak9l6t68mw FOREIGN KEY (appointments_uid) REFERENCES public.appointments(uid);



ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fkgycx9x766lhetpn3w2f8ovd86 FOREIGN KEY (employee_uid) REFERENCES public.employee(uid);



ALTER TABLE ONLY public.owner
    ADD CONSTRAINT fkixykpx18h6slb8tun74344x0i FOREIGN KEY (account_uid) REFERENCES public.account(uid);



ALTER TABLE ONLY public.employee_service
    ADD CONSTRAINT fkonn7jdu7xyg8oqfugecoglnev FOREIGN KEY (employees_uid) REFERENCES public.services(uid);



ALTER TABLE ONLY public.employee_appointments
    ADD CONSTRAINT fkqhjix7h7pp9ykc4xmknqen13 FOREIGN KEY (employee_uid) REFERENCES public.employee(uid);



ALTER TABLE ONLY public.client
    ADD CONSTRAINT fks7byqa1s5jp0jrekt56w5v1gi FOREIGN KEY (account_uid) REFERENCES public.account(uid);



ALTER TABLE ONLY public.employee_service
    ADD CONSTRAINT fkscsf1ruh82ely1wajtd0b34gs FOREIGN KEY (services_uid) REFERENCES public.employee(uid);



ALTER TABLE ONLY public.client_appointments
    ADD CONSTRAINT fkthbdre8cuwtap40rnaclc0i8s FOREIGN KEY (client_uid) REFERENCES public.client(uid);



ALTER TABLE ONLY public.owner
    ADD CONSTRAINT owner_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.account(uid);


