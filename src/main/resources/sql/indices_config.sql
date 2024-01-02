create table public.indices_config
(
    id                bigint       not null
        constraint indices_config_pk
            primary key,
    name              varchar(100) not null
        constraint indices_config_pk_2
            unique,
    index_name        varchar(100) not null,
    index_params      varchar(100) not null,
    remark            varchar(200),
    index_param_types varchar(100) not null
);

comment on column public.indices_config.name is '指标自定义名称';

comment on column public.indices_config.index_name is '指标名称';

comment on column public.indices_config.index_params is '参数列表，逗号分隔';

comment on column public.indices_config.remark is '备注';

alter table public.indices_config
    owner to "capital-features";

create index indices_config_index_name_index
    on public.indices_config (index_name);

create index indices_config_name_index
    on public.indices_config (name);

