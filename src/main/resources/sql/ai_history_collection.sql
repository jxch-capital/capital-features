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

