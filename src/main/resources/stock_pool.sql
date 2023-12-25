create table public.stock_pool
(
    id          bigint                                      not null
        constraint stock_pool_pk
            primary key,
    pool_name   varchar(20)                                 not null,
    pool_stocks text,
    engine      varchar(20)                                 not null,
    interval    varchar(10) default '1d'::character varying not null,
    start_date  date,
    end_date    date
);

comment on table public.stock_pool is '股票池';

comment on column public.stock_pool.pool_name is '股票池名称';

comment on column public.stock_pool.pool_stocks is '股票池代码';

comment on column public.stock_pool.engine is '支持股票代码的引擎';

comment on column public.stock_pool.interval is '间隔';

comment on column public.stock_pool.start_date is '开始时间';

comment on column public.stock_pool.end_date is '结束时间';

alter table public.stock_pool
    owner to "capital-features";

