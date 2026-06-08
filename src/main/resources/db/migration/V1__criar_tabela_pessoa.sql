-- V1__criar_tabela_pessoa.sql
CREATE TABLE pessoa (
    id        UUID         NOT NULL DEFAULT gen_random_uuid(),
    nome      VARCHAR(150) NOT NULL,
    cpf       VARCHAR(11)  NOT NULL,
    telefone  VARCHAR(20)  NOT NULL,

    CONSTRAINT pk_pessoa            PRIMARY KEY (id),
    CONSTRAINT uq_pessoa_cpf        UNIQUE (cpf),
    CONSTRAINT ck_pessoa_cpf_digits CHECK (cpf ~ '^\d{11}$'),
    CONSTRAINT ck_pessoa_nome_vazio CHECK (TRIM(nome) <> '')
);
