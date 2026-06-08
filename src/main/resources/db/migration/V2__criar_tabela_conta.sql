-- V2__criar_tabela_conta.sql
CREATE TABLE conta (
    id         UUID           NOT NULL DEFAULT gen_random_uuid(),
    pessoa_id  UUID           NOT NULL,
    numero     VARCHAR(20)    NOT NULL,
    digito     INTEGER        NOT NULL,
    saldo      NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
    tipo_conta VARCHAR(10)    NOT NULL,

    CONSTRAINT pk_conta                PRIMARY KEY (id),
    CONSTRAINT uq_conta_numero         UNIQUE (numero),
    CONSTRAINT fk_conta_pessoa         FOREIGN KEY (pessoa_id)
                                           REFERENCES pessoa (id)
                                           ON DELETE RESTRICT,
    CONSTRAINT ck_conta_saldo_positivo CHECK (saldo >= 0),
    CONSTRAINT ck_conta_digito         CHECK (digito BETWEEN 0 AND 9),
    CONSTRAINT ck_conta_numero_digits  CHECK (numero ~ '^\d+$')
);

ALTER TABLE conta
    ADD CONSTRAINT ck_conta_tipo_conta
    CHECK (tipo_conta IN ('CORRENTE', 'POUPANCA'));

CREATE INDEX idx_conta_pessoa_id ON conta (pessoa_id);
