-- V4__criar_tabela_log_auditoria.sql
CREATE TABLE log_auditoria_transferencia (
    id                       UUID            NOT NULL DEFAULT gen_random_uuid(),
    transferencia_id         UUID            NOT NULL,
    valor                    NUMERIC(19, 2)  NOT NULL,
    data_hora                TIMESTAMPTZ     NOT NULL,
    conta_origem_id          UUID            NOT NULL,
    conta_origem_numero      VARCHAR(20)     NOT NULL,
    conta_origem_tipo        VARCHAR(10)     NOT NULL,
    conta_origem_saldo_apos  NUMERIC(19, 2)  NOT NULL,
    pessoa_origem_id         UUID            NOT NULL,
    pessoa_origem_nome       VARCHAR(150)    NOT NULL,
    pessoa_origem_cpf        VARCHAR(11)     NOT NULL,
    conta_destino_id         UUID            NOT NULL,
    conta_destino_numero     VARCHAR(20)     NOT NULL,
    conta_destino_tipo       VARCHAR(10)     NOT NULL,
    conta_destino_saldo_apos NUMERIC(19, 2)  NOT NULL,
    pessoa_destino_id        UUID            NOT NULL,
    pessoa_destino_nome      VARCHAR(150)    NOT NULL,
    pessoa_destino_cpf       VARCHAR(11)     NOT NULL,
    registrado_em            TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_log_auditoria              PRIMARY KEY (id),
    CONSTRAINT ck_log_valor_positivo         CHECK (valor > 0),
    CONSTRAINT ck_log_saldo_origem_positivo  CHECK (conta_origem_saldo_apos  >= 0),
    CONSTRAINT ck_log_saldo_destino_positivo CHECK (conta_destino_saldo_apos >= 0)
);

CREATE INDEX idx_log_transferencia_id  ON log_auditoria_transferencia (transferencia_id);
CREATE INDEX idx_log_conta_origem_id   ON log_auditoria_transferencia (conta_origem_id);
CREATE INDEX idx_log_conta_destino_id  ON log_auditoria_transferencia (conta_destino_id);
CREATE INDEX idx_log_pessoa_origem_id  ON log_auditoria_transferencia (pessoa_origem_id);
CREATE INDEX idx_log_pessoa_destino_id ON log_auditoria_transferencia (pessoa_destino_id);
CREATE INDEX idx_log_data_hora         ON log_auditoria_transferencia (data_hora DESC);
