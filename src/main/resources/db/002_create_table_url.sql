create table if not exists url (
    id serial primary key,
    calls_number     int default 0,
    key      text not null unique,
    url       text not null unique,
    owner_id  int not null references registrations(id)
);
comment on table url is 'Ссылки сайта';
comment on column url.id is 'Идентификатор ссылки';
comment on column url.calls_number is 'Количество вызовов ссылки';
comment on column url.key is 'Преобразованная ссылка';
comment on column url.url is 'Оригинальная ссылка';
comment on column url.owner_id is 'Ссылка на регистрируемый сайт';