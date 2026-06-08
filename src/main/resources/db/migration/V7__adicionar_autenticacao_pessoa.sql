-- V7__adicionar_autenticacao_pessoa.sql

ALTER TABLE pessoa
    ADD COLUMN email VARCHAR(180) NULL,
    ADD COLUMN senha VARCHAR(100) NULL,
    ADD COLUMN role  VARCHAR(20)  NOT NULL DEFAULT 'USER';

-- Email deve ser único (um login por pessoa)
ALTER TABLE pessoa
    ADD CONSTRAINT uq_pessoa_email UNIQUE (email);

-- Validação básica de formato de e-mail diretamente no banco
ALTER TABLE pessoa
    ADD CONSTRAINT ck_pessoa_email_formato CHECK (email LIKE '%@%.%');

COMMENT ON COLUMN pessoa.email IS 'E-mail de login';
COMMENT ON COLUMN pessoa.senha IS 'Senha criptografada';
COMMENT ON COLUMN pessoa.role  IS 'Perfil de acesso: USER ou ADMIN';
