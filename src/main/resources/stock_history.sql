create table public.stock_history
(
    stock_pool_id bigint           not null,
    stock_code    varchar(20)      not null,
    date          timestamp        not null,
    open          double precision,
    close         double precision not null,
    high          double precision,
    low           double precision,
    volume        bigint,
    id            bigint           not null
        constraint stock_history_pk
            primary key
);

comment on table public.stock_history is '股票历史';

comment on column public.stock_history.stock_pool_id is '股票池ID';

comment on column public.stock_history.stock_code is '股票代码';

comment on column public.stock_history.date is '时间';

comment on column public.stock_history.open is '开盘';

comment on column public.stock_history.close is '收盘';

comment on column public.stock_history.high is '最高价';

comment on column public.stock_history.low is '最低价';

comment on column public.stock_history.volume is '成交量';

alter table public.stock_history
    owner to "capital-features";

create index stock_history_date_index
    on public.stock_history (date);

create index stock_history_stock_code_index
    on public.stock_history (stock_code);

create index stock_history_stock_pool_id_index
    on public.stock_history (stock_pool_id);

