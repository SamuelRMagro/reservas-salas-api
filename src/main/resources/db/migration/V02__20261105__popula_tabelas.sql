INSERT INTO tb_usuarios (nome, email, ativo)
VALUES ('Ana Souza', 'ana.souza@email.com', TRUE),
       ('Bruno Lima', 'bruno.lima@email.com', TRUE),
       ('Carla Mendes', 'carla.mendes@email.com', TRUE),
       ('Diego Ferreira', 'diego.ferreira@email.com', TRUE),
       ('Eduarda Rocha', 'eduarda.rocha@email.com', TRUE),
       ('Felipe Santos', 'felipe.santos@email.com', TRUE),
       ('Gabriela Alves', 'gabriela.alves@email.com', TRUE),
       ('Henrique Costa', 'henrique.costa@email.com', FALSE),
       ('Isabela Martins', 'isabela.martins@email.com', TRUE),
       ('João Pereira', 'joao.pereira@email.com', TRUE);

INSERT INTO tb_salas (nome, capacidade, localizacao, ativa)
VALUES ('Sala Alfa', 10, 'Bloco A - 1º andar', TRUE),
       ('Sala Beta', 20, 'Bloco A - 2º andar', TRUE),
       ('Sala Gama', 6, 'Bloco A - 2º andar', TRUE),
       ('Sala Delta', 15, 'Bloco B - Térreo', TRUE),
       ('Sala Ômega', 12, 'Bloco B - 1º andar', TRUE),
       ('Auditório Central', 100, 'Prédio Principal', TRUE),
       ('Laboratório 1', 25, 'Bloco C - Térreo', TRUE),
       ('Laboratório 2', 25, 'Bloco C - 1º andar', TRUE),
       ('Sala de Reunião 1', 8, NULL, TRUE),
       ('Sala de Reunião 2', 8, 'Bloco D - 3º andar', FALSE);

INSERT INTO tb_reservas
(sala_id, usuario_id, inicio, fim, quantidade_participantes, status, cancelada_em)
SELECT s.id
     , u.id
     , v.inicio::timestamp, v.fim::timestamp, v.qtd
     , v.status
     , v.cancelada_em::timestamptz
FROM (VALUES ('Sala Alfa', 'ana.souza@email.com', '2026-11-10 09:00', '2026-11-10 10:30', 8, 'ATIVA', NULL),
             ('Sala Alfa', 'bruno.lima@email.com', '2026-11-10 14:00', '2026-11-10 15:00', 5, 'ATIVA', NULL),
             ('Sala Beta', 'carla.mendes@email.com', '2026-11-11 10:00', '2026-11-11 12:00', 18, 'ATIVA', NULL),
             ('Sala Gama', 'diego.ferreira@email.com', '2026-11-11 08:00', '2026-11-11 09:00', 4, 'CANCELADA',
              '2026-11-09 16:30:00-04'),
             ('Auditório Central', 'eduarda.rocha@email.com', '2026-11-12 19:00', '2026-11-12 22:00', 80, 'ATIVA',
              NULL),
             ('Sala Delta', 'felipe.santos@email.com', '2026-11-12 13:30', '2026-11-12 15:00', 12, 'ATIVA', NULL),
             ('Laboratório 1', 'gabriela.alves@email.com', '2026-11-13 08:00', '2026-11-13 12:00', 20, 'ATIVA', NULL),
             ('Laboratório 2', 'henrique.costa@email.com', '2026-11-13 14:00', '2026-11-13 17:00', 22, 'CANCELADA',
              '2026-11-12 10:15:00-04'),
             ('Sala de Reunião 1', 'isabela.martins@email.com', '2026-11-16 09:00', '2026-11-16 10:00', 6, 'ATIVA',
              NULL),
             ('Sala de Reunião 2', 'joao.pereira@email.com', '2026-11-16 15:00', '2026-11-16 16:00', 7, 'ATIVA', NULL),
             ('Sala Ômega', 'ana.souza@email.com', '2026-11-17 10:00', '2026-11-17 11:30', 10, 'ATIVA', NULL),
             ('Sala Alfa', 'carla.mendes@email.com', '2026-11-18 09:00', '2026-11-18 11:00', 9, 'CANCELADA',
              '2026-11-17 08:00:00-04')) AS v(sala, email, inicio, fim, qtd, status, cancelada_em)
         JOIN tb_salas s ON s.nome = v.sala
         JOIN tb_usuarios u ON u.email = v.email;