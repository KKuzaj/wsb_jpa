INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES
(2, 'al. Jana Pawła II 21', NULL, 'Kraków', '31-123'),
(1, 'ul. Długa 15', 'mieszkanie 4', 'Warszawa', '00-238');

INSERT INTO DOCTOR (id, first_name, last_name, telephone_number, email, doctor_number, specialization)
 VALUES (101, 'Anna', 'Kowalska', '123456789', 'anna.kowalska@example.com', 'DOC001', 'SURGEON'),
(102, 'Jan', 'Nowak', '987654321', 'jan.nowak@example.com', 'DOC002', 'CARDIOLOGIST'),
(103, 'Maria', 'Zielinska', '456123789', 'maria.zielinska@example.com', 'DOC003', 'DERMATOLOGIST'),
(104, 'Piotr', 'Wiśniewski', '321654987', 'piotr.wisniewski@example.com', 'DOC004', 'ORTHOPEDIC'),
(105, 'Ewa', 'Kowalczyk', '654789123', 'ewa.kowalczyk@example.com', 'DOC005', 'OCULIST'),
(106, 'Paweł', 'Lewandowski', '666888444', 'pawel.lewandowski@example.com', 'DOC006', 'NEUROLOGIST'),
(107, 'Magdalena', 'Mazur', '777999333', 'magdalena.mazur@example.com', 'DOC007', 'PEDIATRICIAN'),
(108, 'Krzysztof', 'Kaczmarek', '600700800', 'krzysztof.kaczmarek@example.com', 'DOC008', 'PSYCHIATRIST');

INSERT INTO PATIENT (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth) VALUES
(101, 'Jan', 'Kowalski', '114562742', 'jan.kowalski@example.com', 'PAT001', '1990-05-10'),
(102, 'Anna', 'Nowak', '98764321', 'anna.nowak@example.com', 'PAT002', '1985-03-15'),
(103, 'Katarzyna', 'Wiśniewska', '515234678', 'katarzyna.wisniewska@example.com', 'PAT003', '1978-11-22'),
(104, 'Michał', 'Zalewski', '601345678', 'michal.zalewski@example.com', 'PAT004', '2000-02-29'),
(105, 'Agnieszka', 'Kubiak', '784561234', 'agnieszka.kubiak@example.com', 'PAT005', '1992-07-08'),
(106, 'Tomasz', 'Wójcik', '795345987', 'tomasz.wojcik@example.com', 'PAT006', '1980-01-15');

INSERT INTO VISIT (ID, description, time, doctor_id, patient_id) VALUES
(101, 'USG', '2025-04-15T10:00:00', 104, 101),
(102, 'RTG', '2025-04-20T14:30:00', 102, 102),
(103, 'EKG', '2025-05-05T10:45', 102, 102),
(104, 'USG', '2025-04-22T09:00:00', 106, 103),
(105, 'RTG', '2025-04-25T11:30:00', 107, 104),
(106, 'EKG', '2025-04-28T15:00:00', 108, 105),
(107, 'RTG', '2025-04-18T13:45:00', 104, 106),
(108, 'USG', '2025-04-19T10:15:00', 105, 103),
(109, 'EKG', '2025-04-27T08:30:00', 101, 105);

INSERT INTO MEDICAL_TREATMENT (description, type, visit_id)
VALUES ('Konsultacja pediatryczna', 'USG', 101),
('Zastrzyk przeciwbólowy', 'RTG', 102),
('Edukacja zdrowotna', 'EKG', 103);