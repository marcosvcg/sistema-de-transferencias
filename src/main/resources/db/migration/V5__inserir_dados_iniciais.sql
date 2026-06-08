-- V5__inserir_dados_iniciais.sql
WITH
pessoas_inseridas AS (
    INSERT INTO pessoa (nome, cpf, telefone)
    VALUES
        ('Carlos Eduardo Oliveira', '04819263701', '11987654321'),
        ('Ana Paula Souza',         '37265819040', '21976543210'),
        ('Roberto Mendes Lima',     '52130974862', '31965432109'),
        ('Fernanda Costa Rocha',    '68904531279', '41954321098'),
        ('Lucas Almeida Santos',    '91537402685', '51943210987')
    RETURNING id, nome
),
carlos   AS (SELECT id FROM pessoas_inseridas WHERE nome = 'Carlos Eduardo Oliveira'),
ana      AS (SELECT id FROM pessoas_inseridas WHERE nome = 'Ana Paula Souza'),
roberto  AS (SELECT id FROM pessoas_inseridas WHERE nome = 'Roberto Mendes Lima'),
fernanda AS (SELECT id FROM pessoas_inseridas WHERE nome = 'Fernanda Costa Rocha'),
lucas    AS (SELECT id FROM pessoas_inseridas WHERE nome = 'Lucas Almeida Santos')

INSERT INTO conta (pessoa_id, numero, digito, saldo, tipo_conta)
VALUES
    ((SELECT id FROM carlos),   '10020030001', 4, 5250.00,  'CORRENTE'),
    ((SELECT id FROM carlos),   '10020030002', 7, 1800.75,  'POUPANCA'),
    ((SELECT id FROM ana),      '20030040001', 2, 12300.50, 'CORRENTE'),
    ((SELECT id FROM ana),      '20030040002', 9, 4500.00,  'POUPANCA'),
    ((SELECT id FROM roberto),  '30040050001', 1, 750.30,   'CORRENTE'),
    ((SELECT id FROM fernanda), '40050060001', 6, 22000.00, 'CORRENTE'),
    ((SELECT id FROM lucas),    '50060070001', 3, 310.80,   'POUPANCA');
