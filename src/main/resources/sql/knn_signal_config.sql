create table public.knn_signal_config
(
    id                                bigint               not null
        constraint knn_signal_config_pk
            primary key,
    name                              varchar(100)         not null
        constraint knn_signal_config_pk_2
            unique,
    distance                          varchar(100)         not null,
    stock_pool_id                     bigint               not null,
    indices_com_id                    bigint,
    is_normalized                     boolean default true not null,
    start_date                        timestamp            not null,
    end_date                          timestamp            not null,
    size                              integer              not null,
    future_size                       integer              not null,
    neighbor_size                     integer              not null,
    codes                             text                 not null,
    stock_engine                      varchar(100),
    last_update_time_consuming_second bigint,
    remark                            text,
    version                           bigint               not null,
    last_update_version               bigint
);

comment on column public.knn_signal_config.name is '配置名称';

comment on column public.knn_signal_config.distance is '距离算法';

comment on column public.knn_signal_config.stock_pool_id is '股票池ID，见stock_pool.id';

comment on column public.knn_signal_config.indices_com_id is '指标序列ID，见indices_combination.id，为null则不使用指标计算（只计算K线的近邻）';

comment on column public.knn_signal_config.is_normalized is '归一化';

comment on column public.knn_signal_config.start_date is '开始时间';

comment on column public.knn_signal_config.end_date is '结束时间';

comment on column public.knn_signal_config.size is '片段大小';

comment on column public.knn_signal_config.future_size is '预测未来数';

comment on column public.knn_signal_config.neighbor_size is '近邻数';

comment on column public.knn_signal_config.codes is '要预测的股票代码，用逗号分隔';

comment on column public.knn_signal_config.stock_engine is '股票引擎，如果不填就默认使用 stock_pool.id 对应的引擎';

comment on column public.knn_signal_config.last_update_time_consuming_second is '上次更新耗时';

comment on column public.knn_signal_config.remark is '备注';

comment on column public.knn_signal_config.version is '版本号';

alter table public.knn_signal_config
    owner to "capital-features";

create index knn_signal_config_name_index
    on public.knn_signal_config (name);

