-- V3__criar_tabela_transferencia.sql
CREATE TABLE transferencia (
    id               UUID           NOT NULL DEFAULT gen_random_uuid(),
    conta_origem_id  UUID           NOT NULL,
    conta_destino_id UUID           NOT NULL,
    valor            NUMERIC(19, 2) NOT NULL,
    data_hora        TIMESTAMPTZ    NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_transferencia                  PRIMARY KEY (id),
    CONSTRAINT fk_transferencia_origem           FOREIGN KEY (conta_origem_id)
                                                     REFERENCES conta (id)
                                                     ON DELETE RESTRICT,
    CONSTRAINT fk_transferencia_destino          FOREIGN KEY (conta_destino_id)
                                                     REFERENCES conta (id)
                                                     ON DELETE RESTRICT,
    CONSTRAINT ck_transferencia_contas_distintas CHECK (conta_origem_id <> conta_destino_id),
    CONSTRAINT ck_transferencia_valor_positivo   CHECK (valor > 0)
);

CREATE INDEX idx_transferencia_conta_origem  ON transferencia (conta_origem_id);
CREATE INDEX idx_transferencia_conta_destino ON transferencia (conta_destino_id);
CREATE INDEX idx_transferencia_data_hora     ON transferencia (data_hora DESC);
