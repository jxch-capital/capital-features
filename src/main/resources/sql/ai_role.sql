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

