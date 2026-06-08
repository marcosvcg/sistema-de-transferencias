-- V8__popular_autenticacao_dados_iniciais.sql
DO $$
DECLARE
    senha_padrao CONSTANT VARCHAR(100) :=
        '$2a$12$rhLO1a2SV5kodDjUtjTD3OZLFUHOA8J/1uOQM7gQK1jbXBXR1WCTy';
BEGIN
    UPDATE pessoa SET email = 'carlos@email.com',   senha = senha_padrao WHERE cpf = '04819263701';
    UPDATE pessoa SET email = 'ana@email.com',       senha = senha_padrao WHERE cpf = '37265819040';
    UPDATE pessoa SET email = 'roberto@email.com',   senha = senha_padrao WHERE cpf = '52130974862';
    UPDATE pessoa SET email = 'fernanda@email.com',  senha = senha_padrao WHERE cpf = '68904531279';
    UPDATE pessoa SET email = 'lucas@email.com',     senha = senha_padrao WHERE cpf = '91537402685';
END;
$$;

ALTER TABLE pessoa ALTER COLUMN email SET NOT NULL;
ALTER TABLE pessoa ALTER COLUMN senha SET NOT NULL;
