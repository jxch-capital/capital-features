create sequence public.comm_sequence
    increment by 50
    cache 100;

alter sequence public.comm_sequence owner to "capital-features";

