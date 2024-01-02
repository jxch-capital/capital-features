create table public.indices_combination
(
    id          bigint        not null
        constraint indices_combination_pk
            primary key,
    name        varchar(40)   not null
        constraint indices_combination_pk_2
            unique,
    indices_ids varchar(1000) not null,
    max_length  integer       not null,
    remark      varchar(1000)
);

comment on table public.indices_combination is '指标组合';

comment on column public.indices_combination.name is '指标组合名称';

comment on column public.indices_combination.indices_ids is '指标组合ID';

comment on column public.indices_combination.max_length is '最大长度，保证所有指标都不为空的最大长度';

comment on column public.indices_combination.remark is '备注';

alter table public.indices_combination
    owner to "capital-features";

