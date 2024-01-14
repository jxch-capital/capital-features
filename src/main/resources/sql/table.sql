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

create index stock_history_stock_code_date_index
    on public.stock_history (stock_code, date);

create table public.stock_base
(
    id   bigint       not null
        constraint stock_base_pk
            primary key,
    code varchar(20)  not null,
    name varchar(100) not null
);

comment on column public.stock_base.id is '主键';

comment on column public.stock_base.code is '股票代码';

comment on column public.stock_base.name is '股票名称';

alter table public.stock_base
    owner to "capital-features";

create index stock_base_code_index
    on public.stock_base (code);

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

create table public.ai_role
(
    id     bigint       not null
        constraint ai_role_pk
            primary key,
    name   varchar(100) not null,
    text   text         not null,
    remark text
);

comment on column public.ai_role.name is '角色名称';

comment on column public.ai_role.text is '提示词';

comment on column public.ai_role.remark is '备注';

alter table public.ai_role
    owner to "capital-features";

create index ai_role_name_index
    on public.ai_role (name);

create table public.ai_history_collection
(
    id          bigint       not null
        constraint ai_history_collection_pk
            primary key,
    name        varchar(100) not null,
    history_ids text,
    remark      text
);

comment on column public.ai_history_collection.history_ids is 'id，逗号分割';

alter table public.ai_history_collection
    owner to "capital-features";

create index ai_history_collection_name_index
    on public.ai_history_collection (name);

create table public.ai_history
(
    id     bigint not null
        constraint ai_history_pk
            primary key,
    name   text,
    text   text,
    remark bigint
);

alter table public.ai_history
    owner to "capital-features";

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

create index knn_signal_history_knn_signal_config_id_code_index
    on public.knn_signal_history (knn_signal_config_id, code);

create table public.notebook_5m
(
    id     bigint       not null
        constraint notebook_5m_pk
            primary key,
    code   varchar(100) not null,
    date   date         not null,
    type   varchar(100) not null,
    key    varchar(1000),
    value  varchar(1000),
    remark text,
    k      varchar(100)
);

comment on column public.notebook_5m.type is 'k: 单k线
kn: k线组合
kkh: 连接两根K线的高点
kkl: 连接两根K线的低点
kh: 高点水平线
kl: 低点水平线
kk2h: 双顶
kk2l: 双底
mark: 标记

多类型用逗号分隔';

comment on column public.notebook_5m.key is 'signal: 买卖信号
mark: 标注
mark_url: 比如手画的图片

';

comment on column public.notebook_5m.k is '目标k线（逗号分隔）：k1,k2,k3（从左到右以此类推）';

alter table public.notebook_5m
    owner to "capital-features";

create index notebook_5m_code_index
    on public.notebook_5m (code);

create index notebook_5m_code_date_index
    on public.notebook_5m (code, date);

create index notebook_5m_date_index
    on public.notebook_5m (date);

create table public.user_config
(
    id       bigint       not null
        constraint user_pk
            primary key,
    username varchar(100) not null
        constraint user_pk_2
            unique,
    email    varchar(100) not null
        constraint user_pk_3
            unique,
    nickname varchar(100)
);

comment on table public.user_config is '用户表';

alter table public.user_config
    owner to "capital-features";

create index user_email_index
    on public.user_config (email);

create index user_username_index
    on public.user_config (username);

create table public.watch_config
(
    id         bigint        not null
        constraint watch_config_pk
            primary key,
    user_id    bigint        not null,
    watch_name varchar(1000) not null,
    remark     text,
    param      text
);

alter table public.watch_config
    owner to "capital-features";

