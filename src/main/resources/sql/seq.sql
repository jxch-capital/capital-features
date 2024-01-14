create sequence public.stock_pool_seq
    increment by 50
    cache 100;

alter sequence public.stock_pool_seq owner to "capital-features";

alter sequence public.stock_pool_seq owned by public.stock_pool.id;

create sequence public.comm_sequence
    increment by 50
    cache 100;

alter sequence public.comm_sequence owner to "capital-features";

