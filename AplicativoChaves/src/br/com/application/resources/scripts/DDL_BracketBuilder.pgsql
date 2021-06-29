CREATE TABLE usuario (

    email VARCHAR(100) NOT NULL,
    nome VARCHAR(60) NOT NULL,
    senha VARCHAR(105) NOT NULL,
    isOrganizador BOOLEAN NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (email)

);

CREATE TABLE organizador(

    cpf VARCHAR(14) NOT NULL,
    email VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(45) NOT NULL,
    eventos_realizados INT NOT NULL DEFAULT 0,
    nota INT NOT NULL DEFAULT 0,
    CONSTRAINT pk_organizador PRIMARY KEY (cpf),
    CONSTRAINT fk_email_organizador FOREIGN KEY(email) REFERENCES usuario(email) ON DELETE CASCADE

);

CREATE TABLE jogo(

    id_jogo SERIAL,
    nome VARCHAR(45) NOT NULL,
    image bytea NOT NULL,
    CONSTRAINT pk_jogo PRIMARY KEY (id_jogo)

);

CREATE TABLE evento(

    id_evento SERIAL,
    nome VARCHAR(45) NOT NULL,
    jogo INT NOT NULL,
    cpf_organizador VARCHAR(14) NOT NULL,
    detalhes VARCHAR(500),
    premio DECIMAL(11,2) NOT NULL DEFAULT 0.00,
    data DATE NOT NULL,
    CONSTRAINT pk_evento PRIMARY KEY (id_evento),
    CONSTRAINT fk_organizador_evento FOREIGN KEY(cpf_organizador) REFERENCES organizador(cpf) ON DELETE CASCADE,
    CONSTRAINT fk_jogo_evento FOREIGN KEY(jogo) REFERENCES jogo(id_jogo) ON DELETE CASCADE

);

CREATE TABLE tipos_torneio(
    id_tipos_torneio INT NOT NULL,
    nome VARCHAR(40) NOT NULL,
    CONSTRAINT pk_tipos_torneio PRIMARY KEY(id_tipos_torneio) ,
    CONSTRAINT unq_nome_tipos_torneio UNIQUE(nome)
);

INSERT INTO tipos_torneio VALUES(1,'Eliminação Simples');
INSERT INTO tipos_torneio VALUES(2,'Eliminação Dupla');

CREATE TABLE chave_torneio(

    id_chave SERIAL,
    tipo INT NOT NULL,
    qtde_participantes INT NOT NULL,
    id_evento INT NOT NULL,
    comecou BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_chave_torneio PRIMARY KEY (id_chave),
    CONSTRAINT fk_evento_chave_torneio FOREIGN KEY(id_evento) REFERENCES evento(id_evento) ON DELETE CASCADE,
    CONSTRAINT fk_tipo_chave_torneio FOREIGN KEY(tipo) REFERENCES tipos_torneio(id_tipos_torneio) ON DELETE CASCADE

);

CREATE TABLE participante(

    id_participante SERIAL,
    nome VARCHAR(45) NOT NULL,
    posicao INT NOT NULL,
    pontos INT NOT NULL DEFAULT 0,
    id_chave INT NOT NULL,
    CONSTRAINT pk_participante PRIMARY KEY (id_participante),
    CONSTRAINT fk_chave_torneio_participante FOREIGN KEY(id_chave) REFERENCES chave_torneio(id_chave) ON DELETE CASCADE

);

CREATE TABLE part_pos(
    id_participante int,
    posicao INT,
    pontos INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_participante_part_pos FOREIGN KEY(id_participante) REFERENCES participante(id_participante) ON DELETE CASCADE
);