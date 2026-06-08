-- V6__converter_tipo_conta_enum_para_varchar.sql
DROP TYPE IF EXISTS tipo_conta;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_conta_tipo_conta'
          AND conrelid = 'conta'::regclass
    ) THEN
        ALTER TABLE conta
            ADD CONSTRAINT ck_conta_tipo_conta
            CHECK (tipo_conta IN ('CORRENTE', 'POUPANCA'));
    END IF;
END;
$$;
