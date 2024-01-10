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

