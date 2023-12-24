create table stock_pool
(
    id          int8        not null
        constraint stock_pool_pk
            primary key,
    pool_name   varchar(20) not null,
    pool_stocks text,
    engine      varchar(20) not null
);

comment on table stock_pool is '股票池';

comment on column stock_pool.pool_name is '股票池名称';

comment on column stock_pool.pool_stocks is '股票池代码';

comment on column stock_pool.engine is '支持股票代码的引擎';

create sequence public.stock_pool_seq
    increment by 50
    cache 100;

alter sequence public.stock_pool_seq owner to "capital-features";

alter sequence public.stock_pool_seq owned by public.stock_pool.id;

