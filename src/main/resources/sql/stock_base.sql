create table public.stock_base
(
    id   bigint      not null
        constraint stock_base_pk
            primary key,
    code varchar(20) not null,
    name varchar(100)
);

comment on column public.stock_base.id is '主键';

comment on column public.stock_base.code is '股票代码';

comment on column public.stock_base.name is '股票名称';

alter table public.stock_base
    owner to "capital-features";

create index stock_base_code_index
    on public.stock_base (code);

