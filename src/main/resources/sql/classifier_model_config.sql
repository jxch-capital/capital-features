create table public.classifier_model_config
(
    id             bigint       not null
        constraint classifier_model_config_pk
            primary key,
    name           varchar(100) not null,
    stock_pool_id  bigint       not null,
    size           integer      not null,
    future_num     integer      not null,
    indices_com_id bigint,
    classifier_id  bigint       not null,
    remark         varchar(1000)
);

comment on column public.classifier_model_config.name is '模型名称';

comment on column public.classifier_model_config.stock_pool_id is '股票池ID, 见stock_pool.id';

comment on column public.classifier_model_config.size is '片段大小';

comment on column public.classifier_model_config.future_num is '预测的K线数量';

comment on column public.classifier_model_config.indices_com_id is '使用的指标序列id, 见indices_combination.id';

comment on column public.classifier_model_config.classifier_id is '使用的分类器id，见 classifier_config.id';

alter table public.classifier_model_config
    owner to "capital-features";

create index classifier_model_config_name_index
    on public.classifier_model_config (name);

