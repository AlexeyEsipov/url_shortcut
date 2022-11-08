create table if not exists registrations (
    id serial primary key,
    login  text not null unique,
    password  text not null,
    site      text not null unique
);
comment on table registrations is 'Регистрируемый сайт';
comment on column registrations.id is 'Идентификатор';
comment on column registrations.login is 'Логин сайта';
comment on column registrations.password is 'Пароль сайта';
comment on column registrations.site is 'Название сайта';