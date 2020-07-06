create table service_order(
	id bigint not null auto_increment,
    cliente_id bigint not null,
    descricao text not null,
    preco decimal (10,2) not null,
    status varchar(20) not null,
    data_abertura datetime not null,
    data_fechamento datetime,
    
    primary key (id)
);
alter table service_order add constraint fk_service_order_cliente foreign key (cliente_id) references cliente(id);
