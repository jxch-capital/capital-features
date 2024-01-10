create table public.classifier_config
(
    id                     bigint       not null
        constraint classifier_config_pk
            primary key,
    name                   varchar(100) not null,
    classifier_name        varchar(100) not null,
    classifier_param_types varchar(1000),
    classifier_params      varchar(1000),
    remark                 varchar(1000)
);

comment on column public.classifier_config.name is '名称';

comment on column public.classifier_config.classifier_name is '分类器类名';

comment on column public.classifier_config.classifier_param_types is '分类器入参类型';

comment on column public.classifier_config.classifier_params is '分类器参数';

comment on column public.classifier_config.remark is '备注';

alter table public.classifier_config
    owner to "capital-features";

create index classifier_config_classifier_name_index
    on public.classifier_config (classifier_name);

create index classifier_config_name_index
    on public.classifier_config (name);

