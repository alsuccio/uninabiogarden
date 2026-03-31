CREATE DATABASE unina_biogarden;
USE unina_biogarden;

CREATE TABLE utente (
    id_utente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    tipo_persona ENUM('COLTIVATORE', 'PROPRIETARIO')
);

CREATE TABLE lotto (
    id_lotto INT AUTO_INCREMENT PRIMARY KEY,
    superficie DOUBLE,
    tipo_terreno VARCHAR(50),
    id_proprietario INT, 
    FOREIGN KEY (id_proprietario) REFERENCES utente(id_utente)
);

CREATE TABLE coltura (
    id_coltura INT AUTO_INCREMENT PRIMARY KEY,
    tipo_ortaggio VARCHAR(50),
    tempo_maturazione INT, 
    quantita_media DOUBLE 
);

CREATE TABLE ColtivazioneStagionale (
    id_coltivazione INT AUTO_INCREMENT PRIMARY KEY,
    id_lotto INT,
    id_coltura INT,
    stato VARCHAR(20) DEFAULT 'PIANIFICATO',
    data_inizio DATE,
    FOREIGN KEY (id_lotto) REFERENCES lotto(id_lotto),
    FOREIGN KEY (id_coltura) REFERENCES coltura(id_coltura)
);

CREATE TABLE attivita (
    id_attivita INT AUTO_INCREMENT PRIMARY KEY,
    tipo_categoria VARCHAR(50), 
    tipo_stato VARCHAR(20),    
    data_inizio DATE,
    data_fine DATE,
    quantita_effettiva DOUBLE DEFAULT 0, 
    quantita_prevista DOUBLE DEFAULT 0,
    id_coltivazione INT,
    FOREIGN KEY (id_coltivazione) REFERENCES ColtivazioneStagionale(id_coltivazione)
);

CREATE TABLE notifica (
    id_notifica INT AUTO_INCREMENT PRIMARY KEY,
    contenuto TEXT,
    problema VARCHAR(100), 
    tipo_messaggio VARCHAR(50),
    letta BOOLEAN DEFAULT FALSE,
    id_destinatario INT,
    FOREIGN KEY (id_destinatario) REFERENCES utente(id_utente)
);

INSERT INTO utente (nome, cognome, username, password, tipo_persona) VALUES 
('Mario', 'Rossi', 'contadino1', '12345', 'COLTIVATORE'),
('Luigi', 'Verdi', 'proprietario1', 'qwerty', 'PROPRIETARIO'),
('Anna', 'Bianchi', 'proprietario2', 'test', 'PROPRIETARIO');
SELECT *
FROM utente;

INSERT INTO lotto (superficie, tipo_terreno, id_proprietario) VALUES 
(150.0, 'Argilloso', 2),
(200.0, 'Sabbioso', 2);

INSERT INTO coltura (tipo_ortaggio, tempo_maturazione, quantita_media) VALUES 
('Pomodoro', 90, 5.5),
('Zucchina', 60, 4.0),
('Melanzana', 100, 3.5),
('Lattuga', 45, 1.2);

INSERT INTO ColtivazioneStagionale (id_lotto, id_coltura, stato, data_inizio) VALUES 
(1, 1, 'IN_CORSO', '2026-01-10');

INSERT INTO attivita (tipo_categoria, tipo_stato, data_inizio, data_fine, quantita_effettiva, id_coltivazione) VALUES 
('RACCOLTA', 'COMPLETATO', '2023-04-01', '2026-01-01', 50.0, 1), 
('RACCOLTA', 'COMPLETATO', '2023-04-10', '2026-02-10', 65.5, 1), 
('RACCOLTA', 'COMPLETATO', '2023-04-20', '2026-04-20', 45.0, 1); 