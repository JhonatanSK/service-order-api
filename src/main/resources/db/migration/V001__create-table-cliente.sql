create table cliente(
	id bigint not null auto_increment primary key,
    nome varchar(80) not null,
    email varchar(80) not null,
    telefone varchar(80) not null
);