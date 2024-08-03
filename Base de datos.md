CREATE TABLE pacientes (
    uuid Varchar2(60) primary key,
    Nombre VARCHAR2(50) NOT NULL,
    TSangre VARCHAR2(50) NOT NULL,
    Telefono NUMBER(8) NOT NULL,
    Enfermedad VARCHAR2(100) NOT NULL,
    NumHabitacion NUMBER(8) NOT NULL,
    NumCama NUMBER(8) NOT NULL,
    Medicamento VARCHAR2(200),
    FechaNac VARCHAR2(100) not null,
    HoraMedicacion VARCHAR2(200)
);

select * from pacientes;
