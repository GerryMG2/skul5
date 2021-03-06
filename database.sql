CREATE TABLE ROLE
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE DEPARTMENT
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE MUNICIPALITY
(
    ID            SERIAL PRIMARY KEY,
    NAME          VARCHAR(256) UNIQUE,
    ID_DEPARTMENT INTEGER NOT NULL
);

CREATE TABLE USUARIO
(
    ID        SERIAL PRIMARY KEY,
    USER_NAME VARCHAR(32) NOT NULL,
    PASSWD    VARCHAR(256) NOT NULL,
    NAME      VARCHAR(32),
    LAST_NAME VARCHAR(32),
    ROLE_ID   INTEGER NOT NULL,
    ID_MUNICIPALITY INTEGER,
    BIRTH_DATE  DATE,
    ADDRESS     VARCHAR(256),
    SESION    BOOLEAN NOT NULL DEFAULT FALSE,
    ACTIVE    BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE SCHOOL
(
    ID              SERIAL PRIMARY KEY,
    NAME            VARCHAR(256),
    MUNICIPALITY_ID INTEGER
);

CREATE TABLE STUDENT
(
    ID          SERIAL PRIMARY KEY,
    NAME        VARCHAR(80),
    LAST_NAME   VARCHAR(80),
    LICENSE     VARCHAR(9),
    ADDRESS     VARCHAR(256),
    BIRTH_DATE  DATE,
    TELEPHONE   VARCHAR(32),
    CELLPHONE   VARCHAR(32),
    ID_SCHOOL   INTEGER,
    FATHER_NAME VARCHAR(32),
    MOTHER_NAME VARCHAR(32)
);

CREATE TABLE COURSE
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(256) UNIQUE
);

CREATE TABLE RECORD
(
    ANNIO      INTEGER,
    SEMESTER   INTEGER,
    ID_STUDENT INTEGER,
    ID_COURSE  INTEGER,
    GRADE      REAL
);

ALTER TABLE MUNICIPALITY
    ADD CONSTRAINT FK_DEPARTMENT FOREIGN KEY (ID_DEPARTMENT) REFERENCES DEPARTMENT
        (ID);

ALTER TABLE USUARIO
    ADD CONSTRAINT FK_ROLE FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID);

ALTER TABLE STUDENT
    ADD CONSTRAINT FK_SCHOOL FOREIGN KEY (ID_SCHOOL) REFERENCES SCHOOL (ID);

ALTER TABLE SCHOOL
    ADD CONSTRAINT FK_MUNICIPALITY FOREIGN KEY (MUNICIPALITY_ID) REFERENCES MUNICIPALITY (ID);

ALTER TABLE RECORD
    ADD CONSTRAINT PK_RECORD PRIMARY KEY (ANNIO, SEMESTER, ID_STUDENT, ID_COURSE);

ALTER TABLE RECORD
    ADD CONSTRAINT FK_STUDENT FOREIGN KEY (ID_STUDENT) REFERENCES STUDENT (ID);

ALTER TABLE RECORD
    ADD CONSTRAINT FK_COURSE FOREIGN KEY (ID_COURSE) REFERENCES COURSE (ID);

-- DATOS PARA ROL
INSERT INTO ROLE (NAME)
VALUES (1, 'ADMINISTRADOR'),
       (2, 'COORDINADOR');

--DATOS PARA DEPARTAMENTO
INSERT INTO DEPARTMENT (NAME)
VALUES (1, 'SONSONATE'),
('MORAZÁN'),
('LA PAZ'),
('SAN SALVADOR'),
('LA LIBERTAD'),
('CHALATENANGO'),
('LA UNION'),
('AHUACHAPÁN'),
('SAN MIGUEL'),
('SAN VICENTE'),
('SANTA ANA'),
('USULUTÁN'),
('CUSCATLÁN'),
('CABAÑAS');

-- DATOS PARA MUNICIPIO
INSERT INTO MUNICIPALITY (NAME, ID_DEPARTMENT)
VALUES ('IZALCO', 1),
       ('JUAYÚA', 1),
       ('SAN JULIÁN', 1),
       ('SONSONATE', 1),
       ('CHILANGA', 2),
       ('SAN FRANCISCO GOTERA', 2),
       ('CORINTO', 2),
       ('GUATAJIAGUA', 2),
       ('SAN MIGUEL TEPEZONTES', 3),
       ('SANTA MARÍA OSTUMA', 3),
       ('SAN PEDRO MASAHUAT', 3),
       ('SAN LUIS LA HERRADURA', 3),
       ('PANCHIMALCO', 4),
       ('SOYAPANGO', 4),
       ('NEJAPA', 4),
       ('SAN SALVADOR', 4),
       ('LA LIBERTAD', 5),
       ('ZARAGOZA', 5),
       ('SANTA TECLA', 5),
       ('SAN JUAN OPICO', 5),
       ('AZACUALPA', 6),
       ('SAN IGNACIO', 6),
       ('LA PALMA', 6),
       ('NUEVA CONCEPCIÓN', 6),
       ('SAN ALEJO', 7),
       ('SANTA ROSA DE LIMA', 7),
       ('EL SAUCE', 7),
       ('CONCHAGUA', 7),
       ('AHUACHAPÁN', 8),
       ('SAN FRANCISCO MENÉNDEZ', 8),
       ('EL REFUGIO', 8),
       ('JUJUTLA', 8),
       ('LOLOTIQUE', 9),
       ('SAN MIGUEL', 9),
       ('CIUDAD BARRIOS', 9),
       ('CHINAMECA', 9),
       ('SAN SEBASTIÁN', 10),
       ('SAN ILDEFONSO', 10),
       ('APASTEPEQUE', 10),
       ('SAN VICENTE', 10),
       ('SANTA ANA', 11),
       ('METAPÁN', 11),
       ('SAN SEBASTIÁN SALITRILLO', 11),
       ('CHALCHUAPA', 11),
       ('JIQUILISCO', 12),
       ('JUCUARÁN', 12),
       ('BERLÍN', 12),
       ('USULUTÁN', 12),
       ('SAN JOSÉ GUAYABAL', 13),
       ('COJUTEPEQUE', 13),
       ('EL CARMEN', 13),
       ('SUCHITOTO', 13),
       ('TEJUTEPEQUE', 14),
       ('JUTIAPA', 14),
       ('VICTORIA', 14),
       ('ILOBASCO', 14);

-- DATOS PARA USUARIO
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('RAUL', 'HERRERA', 2, false, true, '6484e7fe84f02520350861bcf8d052590cc332de2465049e37e1f87232bdea5e', 'rherrera', '1995-12-14', 16, 'Condominio Arboledas 520');
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('jito', 'majo', 1, false, false, 'rapidito', 'majito', '2020-07-15', 24, 'en tu casa');
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('Mauricio', 'PEREZ', 2, false, true, '12345', 'aperez', '1991-07-13', 16, 'Colonia Escalon pasaje 8');
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('predro', 'fernandez', 1, false, true, '12345', 'jpineda', '1988-08-17', 14, 'La campanera senda 15 poniente');
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('carlos', 'rolando', 2, false, true, 'admin', 'barrabas', '2020-05-13', 13, 'admin');
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('Yury', 'Castro', 1, false, true, '1234', 'ycastro', '1995-12-14', 5, 'por alli');
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('minaj', 'rosado', 1, false, false, 'sdfsdfss', 'mijangosdsdf', '2020-07-22', 15, 'Su casa');
INSERT INTO public.usuario (name, last_name, role_id, sesion, active, passwd, user_name, birth_date, id_municipality, address) VALUES ('jaime', 'mendez', 2, false, true, 'simon', 'jmendex', '2020-07-09', 18, 'Colonia Escalon pasaje 8');

-- DATOS PARA CENTROS
INSERT INTO SCHOOL (NAME, MUNICIPALITY_ID)
VALUES ('CENTRO ESCOLAR COMUNIDAD EL BAMBU CASERIO ROSARIO DE CEREN CANTON EL SUNZA', 1),
       ('CENTRO ESCOLAR CASERIO COOPERATIVA BUENA VISTA CANTON TALCOMUNCA', 1),
       ('CENTRO ESCOLAR CASERIO SAN MARCELINO', 1),
       ('CENTRO ESCOLAR CASERIO EL MORRO N° 2 CANTON TALCOMUNCA', 1),
       ('CENTRO ESCOLAR CASERIO LOS DIAZ', 2),
       ('CENTRO ESCOLAR CASERIO LA CUMBRE CANTON LOS NARANJOS', 2),
       ('CENTRO ESCOLAR CANTON SAN JUAN DE DIOS', 2),
       ('CENTRO ESCOLAR CASERIO MONTERREY, CANTON LOS CAÑALES', 2),
       ('CENTRO ESCOLAR CASERÍO EL CEDRO', 3),
       ('CENTRO ESCOLAR DOCTOR EDUARDO ENRIQUE BARRIENTOS', 3),
       ('COMPLEJO EDUCATIVO EUGENIO AGUILAR TRIGUEROS', 3),
       ('CENTRO ESCOLAR CANTON EL BEBEDERO', 3),
       ('CENTRO ESCOLAR RAFAEL CAMPO', 4),
       ('CENTRO ESCOLAR CASERIO VISTA HERMOSA, CANTON SALINAS DE AYACACHAPA', 4),
       ('CENTRO ESCOLAR CASERIO LOTIFICACION SAN ANTONIO, CANTON LAS DELICIAS', 4),
       ('CENTRO ESCOLAR MARGINACION EL PROGRESO I Y II', 4),
       ('CENTRO ESCOLAR CASERIO LOS SANCHEZ CANTON PIEDRA PARADA', 5),
       ('CENTRO ESCOLAR CASERIO LAS CRUCES CANTON PIEDRA PARADA', 5),
       ('CENTRO ESCOLAR CASERIO LOS PINEDA', 5),
       ('CENTRO ESCOLAR CANTON EL PEDERNAL', 5),
       ('ESCUELA DE EDUCACION PARVULARIA DE SAN FRANCISCO GOTERA', 6),
       ('CENTRO ESCOLAR PROFESOR SAMUEL CORDOVA', 6),
       ('LICEO CRISTIANO REVERENDO JUAN BUENO DE SAN FRANCISCO GOTERA', 6),
       ('CENTRO ESCOLAR ASENTAMIENTO SAN JOSE', 6),
       ('CENTRO ESCOLAR CASERIO MATAPALO II CANTON CORRALITO', 7),
       ('CENTRO ESCOLAR CASERIO LA TORTUGA CANTON HONDABLE', 7),
       ('CENTRO ESCOLAR CASERIO JALACATE CANTON VARILLA NEGRA', 7),
       ('CENTRO ESCOLAR CASERIO LOS VELASQUEZ CANTON CORRALITO', 7),
       ('CENTRO ESCOLAR GENERAL GERARDO BARRIOS', 8),
       ('CENTRO ESCOLAR CANTON PAJIGUA ABAJO', 8),
       ('CENTRO ESCOLAR CASERIO LA CHARANGA CANTON CIRIGUAL', 8),
       ('ESCUELA DE EDUCACION PARVULARIA DE GUATAJIAGUA', 8),
       ('CENTRO ESCOLAR CANTON SAN BARTOLO', 9),
       ('CENTRO ESCOLAR LA PAZ', 9),
       ('CENTRO ESCOLAR CANTON SOLEDAD LAS FLORES', 9),
       ('INSTITUTO NACIONAL DE SAN MIGUEL TEPEZONTES', 9),
       ('CENTRO ESCOLAR TENIENTE CORONEL JOSE CARLOS FLORES BENITEZ', 10),
       ('CENTRO ESCOLAR CASERIO LOMA DE TEPILO', 10),
       ('ESCUELA DE EDUCACION PARVULARIA PROFESOR JOSE CUPERTINO ROSALES', 10),
       ('CENTRO ESCOLAR CANTON LOMA LARGA', 10),
       ('CENTRO ESCOLAR CASERIO EL CASTAÑO CANTON LAS ISLETAS', 11),
       ('LICEO CRISTIANO REVERENDO JUAN BUENO DEL CANTON EL ACHIOTAL', 11),
       ('CENTRO ESCOLAR CASERIO LA DIVINA PROVIDENCIA', 11),
       ('CENTRO ESCOLAR CANTON LAS HOJAS', 11),
       ('CENTRO ESCOLAR CANTON EL CORDONCILLO', 12),
       ('CENTRO ESCOLAR PLAYA DORADA', 12),
       ('CENTRO ESCOLAR CASERIO SAN CRISTOBAL LA ARENERA', 12),
       ('COMPLEJO EDUCATIVO ANGELA SCORSONELLI', 12),
       ('COMPLEJO EDUCATIVO PROFESOR BERNARDINO VILLAMARIONA', 13),
       ('CENTRO ESCOLAR CANTON EL GUAYABO', 13),
       ('COMPLEJO EDUCATIVO CANTON SAN ISIDRO', 13),
       ('CENTRO ESCOLAR CASERIO AMAYITO', 13),
       ('COLEGIO NUEVO MILENIO', 14),
       ('COLEGIO RICARDO MIRO', 14),
       ('LICEO CRISTIANO LICENCIADO SAMUEL HUMBERTO LEIVA', 14),
       ('CENTRO ESCOLAR BARRIO EL PROGRESO', 14),
       ('INSTITUTO NACIONAL JUAN PABLO II', 15),
       ('COLEGIO PAMPANITOS', 15),
       ('CENTRO ESCOLAR CASERIO EL CASTAÑO CANTON EL CONACASTE', 15),
       ('CENTRO ESCOLAR JOSE MATIAS DELGADO', 15),
       ('COLEGIO NUESTRO MUNDO INFANTIL', 16),
       ('CENTRO ESCOLAR GENERAL FRANCISCO MORAZAN', 16),
       ('CENTRO ESCOLAR MIGUEL PINTO', 16),
       ('COLEGIO LUTERANO SALVADOREÑO', 16),
       ('CENTRO ESCOLAR COMUNIDAD TOLUCA CANTON MELARA', 17),
       ('CENTRO ESCOLAR CANTON SAN RAFAEL', 17),
       ('CENTRO ESCOLAR CANTON LAS MESAS', 17),
       ('ESCUELA DE EDUCACION PARVULARIA DE LA LIBERTAD', 17),
       ('INSTITUTO NACIONAL DE ZARAGOZA', 18),
       ('CENTRO ESCOLAR CANTON EL ZAITE', 18),
       ('COLEGIO MAYA', 18),
       ('COLEGIO DE ZARAGOZA', 18),
       ('COLEGIO CHAMPAGNAT', 19),
       ('CENTRO ESCOLAR CANTON LAS GRANADILLAS', 19),
       ('COLEGIO MARIO MONTEFORTE TOLEDO', 19),
       ('CENTRO INFANTIL BILINGÜE MI PEQUEÑA CASITA', 19),
       ('CENTRO ESCOLAR HACIENDA CHANMICO', 20),
       ('CENTRO ESCOLAR CANTON LOS AMATES', 20),
       ('CENTRO ESCOLAR CANTON SAN PEDRO MARTIR', 20),
       ('COLEGIO JOHN FRANK ADAMS', 20),
       ('ESCUELA DE EDUCACION PARVULARIA SERGIO ROMERO', 21),
       ('CENTRO ESCOLAR PRESBITERO RAUL ANTONIO RIVAS', 21),
       ('CENTRO ESCOLAR CANTON CUESTA MARINA', 21),
       ('INSTITUTO NACIONAL DE AZACUALPA', 21),
       ('CENTRO ESCOLAR CANTON EL ROSARIO', 22),
       ('CENTRO ESCOLAR CASERIO LAS TUNAS CANTON EL CARMEN', 22),
       ('CENTRO ESCOLAR CANTON LAS PILAS', 22),
       ('CENTRO ESCOLAR JOSE MARTI', 22),
       ('CENTRO ESCOLAR CASERIO LAS MINAS, CANTON EL AGUACATAL', 23),
       ('CENTRO ESCOLAR CASERIO HIERBA BUENA CANTON LOS PLANES', 23),
       ('COLEGIO CRISTIANO LICENCIADO EDGARDO ARTURO CABRERA', 23),
       ('CENTRO ESCOLAR CASERIO EL TERRERO, CANTON LOS HORCONES', 23),
       ('CENTRO ESCOLAR CASERIO EL CACAO CANTON  SUNAPA', 24),
       ('CENTRO CULTURAL NUEVA CONCEPCION', 24),
       ('CENTRO ESCOLAR CASERIO CHICUMA, CANTON EL GAVILAN', 24),
       ('CENTRO ESCOLAR CASERIO EL EMBARCAJE CANTON LOS CHILAMATES', 24),
       ('CENTRO ESCOLAR CASERIO EL CRUCILLAL CANTON EL COPALIO', 25),
       ('CENTRO ESCOLAR CANTON SAN JOSE', 25),
       ('CENTRO ESCOLAR CANTON EL TEMPISQUE', 25),
       ('CENTRO ESCOLAR CASERIO EL CARAO CANTON TERRERO BLANCO', 25),
       ('CENTRO ESCOLAR CASERIO EL BARATILLO CANTON SAN SEBASTIAN', 26),
       ('CENTRO ESCOLAR CENTRO AMERICA', 26),
       ('CENTRO ESCOLAR TRINIDAD SANCHEZ DE QUEZADA', 26),
       ('CENTRO ESCOLAR PROFESOR JULIO CESAR CORDERO LARA', 26),
       ('CENTRO ESCOLAR CASERIO VIROLA CANTON CANAIRE', 27),
       ('CENTRO ESCOLAR JOSE FRANCISCO BARRUNDIA', 27),
       ('CENTRO ESCOLAR CRISOGENO PEREZ', 27),
       ('CENTRO ESCOLAR CASERIO LA CANTARERA CANTON CANAIRE', 27),
       ('CENTRO ESCOLAR ISMAEL AVELAR VELASQUEZ CANTON EL CACAO', 28),
       ('CENTRO ESCOLAR CANTON EL CIPRES', 28),
       ('COMPLEJO EDUCATIVO PROFESOR RAUL FLORES MORENO', 28),
       ('CENTRO ESCOLAR CANTON EL PILON', 28),
       ('COMPLEJO EDUCATIVO GENERAL FABIO MORAN', 29),
       ('CENTRO ESCOLAR CANTON SAN RAMON', 29),
       ('COMPLEJO EDUCATIVO DOCTOR RAFAEL MEZA DELGADO', 29),
       ('COLEGIO ALBERTO MASFERRER', 29),
       ('CENTRO ESCOLAR CASERIO EL CHINO, CANTON GARITA PALMERA', 30),
       ('CENTRO ESCOLAR CASERIO LOS PALMOS LA DANTA CANTON LA HACHADURA', 30),
       ('LICEO CRISTIANO LICENCIADO JOAQUIN EDGARDO GARCIA LEMUS', 30),
       ('CENTRO ESCOLAR CASERIO EL CORTIJO CANTON EL COROZO', 30),
       ('CENTRO ESCOLAR COMUNIDAD EL REFUGIO', 31),
       ('CENTRO ESCOLAR CATOLICO NUESTRA SEÑORA DEL REFUGIO', 31),
       ('CENTRO ESCOLAR FRANCISCO GAVIDIA', 31),
       ('CENTRO ESCOLAR CASERIO COMUNIDAD SAN JUAN LA CEIBA CANTON SAN ANTONIO', 31),
       ('CENTRO ESCOLAR LEOPOLDO MAYEN TORRES', 32),
       ('CENTRO ESCOLAR CASERIO VALLE NUEVO CANTON GUAYAPA ABAJO', 32),
       ('CENTRO ESCOLAR CASERIO GUAYAPA ARRIBA, CANTON GUAYAPA ARRIBA', 32),
       ('CENTRO ESCOLAR HACIENDA LAS DELICIAS CANTON SAN ANTONIO', 32),
       ('CENTRO ESCOLAR CASERIO CONACASTILLO', 33),
       ('CENTRO ESCOLAR CASERIO LAS LAJAS', 33),
       ('CENTRO ESCOLAR CANTON SAN RAFAEL DE LABRA', 33),
       ('CENTRO ESCOLAR CANTON SANTA BARBARA', 33),
       ('CENTRO ESCOLAR CANTON LAS DELICIAS', 34),
       ('COLEGIO JOSEFINO NUESTRA SEÑORA DE LA PAZ', 34),
       ('CENTRO ESCOLAR CATOLICO FRANCISCANO ESPIRITU SANTO', 34),
       ('CENTRO ESCOLAR COLONIA SAN FRANCISCO', 34),
       ('CENTRO ESCOLAR CASERIO EL CAULOTE CANTON SAN LUISITO', 35),
       ('COLEGIO EMILIA FERREIRO', 35),
       ('CENTRO ESCOLAR COLONIA MONSEÑOR RODRIGO ORLANDO CABRERA', 35),
       ('CENTRO ESCOLAR CANTON BELEN', 35),
       ('CENTRO ESCOLAR CANTON EL JOCOTILLO', 36),
       ('CENTRO ESCOLAR DR. MANUEL CASTRO RAMIREZ P.', 36),
       ('CENTRO ESCOLAR CANTON LOS PLANES PRIMEROS', 36),
       ('ESCUELA DE EDUCACION PARVULARIA FEDERICO FROEBEL', 36),
       ('CENTRO ESCOLAR CASERIO LA CEBADIA', 37),
       ('CENTRO ESCOLAR CANTON EL PORVENIR AGUACAYO', 37),
       ('CENTRO ESCOLAR CANTON SANTA TERESA', 37),
       ('COMPLEJO EDUCATIVO CATOLICO LA SANTA FAMILIA DE SAN SEBASTIAN', 37),
       ('COMPLEJO EDUCATIVO CASERIO SAN FRANCISCO DE LA CRUZ CANTON GUACHIPILIN', 38),
       ('CENTRO ESCOLAR CASERIO EL QUEBRACHO', 38),
       ('CENTRO ESCOLAR CANTON EL CUMISTE', 38),
       ('CENTRO ESCOLAR CASERIO LAS LAJAS CANTON LAJAS Y CANOAS', 38),
       ('CENTRO ESCOLAR MERCEDES NOVOA', 39),
       ('CENTRO ESCOLAR CANTON SAN JOSE ALMENDROS', 39),
       ('CENTRO ESCOLAR CASERIO SAN FELIPITO CANTON SAN NICOLAS', 39),
       ('CENTRO ESCOLAR CASERIO SANTA PAULA CANTON SAN NICOLAS', 39),
       ('CENTRO ESCOLAR CASERIO RIO FRIO CANTON PARRAS LEMPA', 40),
       ('CENTRO ESCOLAR PARCELACION JIBOA', 40),
       ('COLEGIO DE LA MISION BAUTISTA INTERNACIONAL', 40),
       ('CENTRO ESCOLAR HACIENDA SAN JUAN BUENAVISTA CANTON EL REBELDE', 40),
       ('CENTRO ESCOLAR "URBANIZACION BELLA VISTA", CANTON PRIMAVERA', 41),
       ('CENTRO ESCOLAR CATOLICO SAN LORENZO', 41),
       ('LICEO JUAN ENRIQUE PESCALOZZI', 41),
       ('COLEGIO BILINGÜE LIDIA SALMAN DE BARGAS', 41),
       ('CENTRO ESCOLAR CASERIO COLONIA SAN FRANCISCO CANTON BELEN GUIJAT', 42),
       ('CENTRO ESCOLAR CASERIO EL CARMEN, CANTON EL PANAL', 42),
       ('CENTRO ESCOLAR CASERIO EL AMATAL CANTON SAN JERONIMO', 42),
       ('CENTRO ESCOLAR CASERIO LA CONCHAGUA, CANTON LAS PIEDRAS', 42),
       ('CENTRO ESCOLAR CANTON LOS AMATES', 43),
       ('COMPLEJO EDUCATIVO CIUDAD REAL CANTON LOS AMATES', 43),
       ('COLEGIO HUELLITAS DE ÉXITO', 43),
       ('COLEGIO GLADIS JACQUELINE POSADA DE CLAROS', 43),
       ('CENTRO ESCOLAR DOCTOR RANULFO CASTRO', 44),
       ('COLEGIO BAUTISTA DE CHALCHUAPA', 44),
       ('ESCUELA CRISTIANA CANTON LA MAGDALENA', 44),
       ('CENTRO ESCOLAR CANTON BUENOS AIRES', 44),
       ('CENTRO ESCOLAR CASERIO NUEVA ESPERANZA', 45),
       ('CENTRO ESCOLAR CANTON EL HULE CHACHO', 45),
       ('CENTRO ESCOLAR CANTON EL COYOLITO', 45),
       ('CENTRO ESCOLAR CASERIO NUEVO SAN JUAN CANTON SAN JUAN CRUZADILLA', 45),
       ('CENTRO ESCOLAR CASERIO EL BAJIO CANTON EL ZAPOTE', 46),
       ('CENTRO ESCOLAR CASERIO EL ENCANTADO CANTON EL JUTAL', 46),
       ('CENTRO ESCOLAR CASERIO LAS FLORES CANTON SAMURIA', 46),
       ('CENTRO ESCOLAR CASERIO EL TEMPISCAL CANTON EL ZAPOTE', 46),
       ('CENTRO ESCOLAR CASERIO RIO DE LOS BUEYES', 47),
       ('CENTRO ESCOLAR HERMANO ROBERTO COOK', 47),
       ('CENTRO ESCOLAR CANTON VIRGINIA', 47),
       ('CENTRO ESCOLAR CANTON CONCEPCION', 47),
       ('COMPLEJO EDUCATIVO CANTON LAS SALINAS', 48),
       ('INSTITUTO NACIONAL DE USULUTAN', 48),
       ('CENTRO ESCOLAR CASERIO SAN JAIME', 48),
       ('CENTRO ESCOLAR CEFERINO ALBERTO OSEGUEDA', 48),
       ('CENTRO ESCOLAR CASERIO LOMA CHATA, CANTON EL SALITRE', 49),
       ('CENTRO ESCOLAR CANTON PIEDRA LABRADA', 49),
       ('CENTRO ESCOLAR CANTON ANIMAS', 49),
       ('CENTRO ESCOLAR CASERIO EL FRANCO, CANTON EL SALITRE', 49),
       ('CENTRO ESCOLAR NESTOR SALAMANCA', 50),
       ('CENTRO ESCOLAR WALTER THILO DEININGER', 50),
       ('CENTRO ESCOLAR CANTON JIÑUCO', 50),
       ('ESCUELA DE EDUCACION PARVULARIA MARIA BEDOYA AGUILAR', 50),
       ('CENTRO ESCOLAR CANTON EL ZAPOTAL', 51),
       ('INSTITUTO NACIONAL DE EL CARMEN', 51),
       ('CENTRO ESCOLAR CANTON EL CAULOTILLO', 51),
       ('COMPLEJO EDUCATIVO ANGELINA ANGEL PANAMEÑO', 51),
       ('CENTRO ESCOLAR CASERIO LA MORA CANTON LA MORA', 52),
       ('CENTRO ESCOLAR CANTON BUENA VISTA', 52),
       ('COMPLEJO EDUCATIVO DOCTOR GUILLERMO MANUEL UNGO', 52),
       ('CENTRO ESCOLAR COMUNIDAD LAURA LOPEZ DEL CANTON CONSOLACION', 52),
       ('CENTRO ESCOLAR CANTON LA CONCEPCION', 53),
       ('CENTRO ESCOLAR CANTON LAS LOMAS', 53),
       ('CENTRO ESCOLAR CANTON SANTA OLAYA', 53),
       ('CENTRO ESCOLAR CANTON SANTA RITA', 53),
       ('CENTRO ESCOLAR CANTON SAN SEBASTIAN', 54),
       ('CENTRO ESCOLAR CANTON CAROLINA N°2', 54),
       ('CENTRO ESCOLAR CANTON PALACIOS', 54),
       ('CENTRO ESCOLAR JOSE DE JESUS MENJIVAR', 54),
       ('CENTRO ESCOLAR CANTON LA UVILLA', 55),
       ('CENTRO ESCOLAR CASERIO EL JUTAL CANTON SAN ANTONIO', 55),
       ('COMPLEJO EDUCATIVO 10 DE OCTUBRE 1987', 55),
       ('CENTRO ESCOLAR CASERIO EL ZOPE CANTON AZACUALPA', 55),
       ('CENTRO ESCOLAR CASERIO EL MESTIZO CANTON SAN JOSE', 56),
       ('CENTRO ESCOLAR SENDERO DE LIBERTAD', 56),
       ('COMPLEJO EDUCATIVO CANTON CERRO COLORADO', 56),
       ('CENTRO ESCOLAR CASERIO SAN LUIS GRAMAL CANTON SAN FRANCISCO DEL MONTE', 56);

-- DATOS PARA ESTUDIANTE

INSERT INTO STUDENT (name, last_name, license, address, birth_date, telephone, cellphone, id_school, father_name, mother_name)
VALUES ('Rodrigo Josue', 'Villeda Cruz', '000000001', 'El manguito, senda 14', '1998-01-01', '20222345', '724423432', 1, 'Juan', 'Rosa'),
('Francisco Jose', 'Paredes Guerra', '000000005', 'La rosa, Senda 15', '1986-10-22', '27787885', '774776578', 1, 'Fulano', 'Teresa'),
('Marcos Sebastian', 'Quintanilla Hernandez', '000000003', 'El encuentro, Senda 22', '1995-05-07', '21535435', '743243649', 1, 'Armando', 'Gabriela'),
('Adrian Alonso', 'Hernandez Paredes', '000000004', 'El limon, Senda 18', '1990-03-30', '25528523', '742535258', 1, 'Jose', 'Maria'),
('Alfredo Ruben', 'Cruz Quintanilla', '000000002', 'Las moras, Senda 12', '2000-07-05', '22574588', '775847855', 1, 'Julian', 'Mercedes'),
('josesito', 'asals', '789654123', 'salsnalsk', '2020-07-16', '78945612', '12345678', 94, '', ''),
('abigail', 'montez', '000381177', '123 park way av', '2020-07-22', '98785456', '77411525', 2, '', '');

-- DATOS PARA MATERIA
INSERT INTO public.course (name) VALUES ('matematica');
INSERT INTO public.course (name) VALUES ('Física');
INSERT INTO public.course (name) VALUES ('Historia');
INSERT INTO public.course (name) VALUES ('Filosofia');

-- DATOS PARA REGISTRO
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2020, 1, 1, 2, 5.9);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2020, 1, 1, 1, 8);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2019, 1, 2, 2, 8);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2019, 2, 4, 1, 9);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2019, 2, 4, 2, 10);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2019, 2, 1, 2, 3);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2019, 1, 1, 2, 2.4);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2019, 2, 2, 1, 8.52);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (2017, 1, 2, 1, 1);
INSERT INTO public.record (annio, semester, id_student, id_course, grade) VALUES (5, 2, 4, 1, 0);

