create table public.knn_signal_history
(
    id                   bigint       not null
        constraint knn_signal_history_pk
            primary key,
    knn_signal_config_id bigint       not null,
    code                 varchar(100) not null,
    date                 timestamp    not null,
    signal               integer,
    knn_version          bigint
);

comment on column public.knn_signal_history.knn_signal_config_id is '见knn_signal_config.id';

comment on column public.knn_signal_history.code is '股票代码';

comment on column public.knn_signal_history.date is '时间';

comment on column public.knn_signal_history.signal is '信号';

comment on column public.knn_signal_history.knn_version is '数据更新来自哪个KNN版本';

alter table public.knn_signal_history
    owner to "capital-features";

create index knn_signal_history_code_index
    on public.knn_signal_history (code);

create index knn_signal_history_date_index
    on public.knn_signal_history (date);

create index knn_signal_history_knn_signal_config_id_index
    on public.knn_signal_history (knn_signal_config_id);

