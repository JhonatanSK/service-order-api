create table comentario(
	id bigint not null auto_increment,
    ordem_servico_id bigint not null,
    descricao text not null,
	data_envio datetime not null,
    
    primary key(id)
);
alter table comentario add constraint fk_comentario_service_order foreign key (ordem_servico_id) references service_order(id);