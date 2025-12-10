SISTEMA DE GESTIÓN DE CURSOS – README

## Descripción General
Este proyecto implementa un Sistema de Gestión de Profesores, Alumnos y Cursos desarrollado en Java
El sistema permite registrar, editar, eliminar y consultar profesores, alumnos y cursos, además de gestionar inscripciones y asignaciones.

---

# Compilación y Ejecución

Dentro de la carpeta `src`

Compilar:
javac *.java

Ejecutar:
java SistemaGestionCursos

Los archivos de persistencia se generan automáticamente al salir del programa.

---
#  Características Principales

## Profesores
- Registrar profesores con:
  - Nombre completo
  - Niveles que imparte
  - Categorías/áreas
  - Título académico
  - Año del título
- Editar profesor por atributo
- Eliminar únicamente si no está asignado a cursos
- Listado detallado de profesores
- Persistencia en `profesores.txt`

---

## Alumnos
- Registrar alumnos con:
  - Nombre completo (solo letras)
  - Edad
  - Escuela de procedencia (solo letras)
- Editar cualquier atributo del alumno (menú selectivo)
- Eliminar solo si no está inscrito en cursos
- Persistencia en `alumnos.txt`

---

## Cursos
- Crear cursos con:
  - Nombre, nivel, categoría
  - Horario validado
  - Plataforma
  - Cupo máximo
  - Descripción
  - Fechas de inscripción y curso
- Reglas importantes:
  - Fechas válidas, consistentes y posteriores al día actual
  - Inicio del curso > Fin de inscripciones
  - Edición permitida solo si:
    - El curso no ha iniciado
    - Tiene más de 3 alumnos
  - Eliminación permitida solo si:
    - No tiene alumnos inscritos  
    - O ya finalizó
  - Asignación de profesor:
    - Solo si cumple **nivel** y **categoría**
    - Solo si el curso no ha iniciado
  - Inscripción de alumnos:
    - Dentro del periodo
    - Con cupo disponible
    - No repetido
    - Máximo 6 cursos activos por alumno

- Listado detallado de cursos incluye:
  - Profesor asignado
  - Alumnos inscritos
  - Fechas completas
  - Horario y plataforma

- Persistencia en `cursos.txt`, con:
  - ID del profesor asignado
  - IDs de alumnos inscritos

---

# Estructura del Proyecto

src/
│
├── SistemaGestionCursos.java       → Clase principal (main)
├── MenuConsola.java                → Interfaz por consola
│
├── Persona.java                    → Clase abstracta base
├── Profesor.java                   → Modelo de profesor + serialización
├── Alumno.java                     → Modelo de alumno + serialización
├── Curso.java                      → Modelo de curso + serialización
│
├── Identificable.java              → Interface para objetos con ID único
├── Persistible.java                → Interface de serialización
│
├── RepositorioBase.java            → Clase abstracta para repositorios basados en arreglos
├── RepositorioProfesores.java
├── RepositorioAlumnos.java
├── RepositorioCursos.java
│
├── GestorProfesores.java           → Lógica de profesores
├── GestorAlumnos.java              → Lógica de alumnos
├── GestorCursos.java               → Lógica de cursos
│
└── Excepciones/
    ├── DatosInvalidosException.java
    ├── NoEncontradoException.java
    ├── CapacidadLlenaException.java
    ├── InscripcionException.java
    ├── AsignacionProfesorException.java
    ├── EliminacionNoPermitidaException.java
    ├── FormatoInvalidoException.java

---

# Persistencia

El sistema guarda automáticamente la información en archivos de texto:

- `profesores.txt`
- `alumnos.txt`
- `cursos.txt`

Cada clase persistente implementa:

String toLineaTexto();
void fromLineaTexto(String linea);

Esto permite convertir objetos en líneas de texto y reconstruirlos al iniciar el sistema.

---

# Validaciones Importantes

### Nombres y texto
- Solo letras para nombres de personas y escuelas.

### Horarios
- Formato `HH:MM-HH:MM`
- Rango permitido: 07:00 a 21:00

### Fechas
- Formato `DD-MM-AAAA`
- No pueden ser anteriores a hoy
- Deben guardar coherencia:
  - Inicio inscripciones ≤ Fin inscripciones
  - Inicio curso > Fin inscripciones
  - Fin curso ≥ Inicio curso

### Inscripciones
- Solo dentro del periodo del curso
- Cupo disponible
- Alumno no repetido
- Máximo 6 cursos activos por alumno

### Asignación de profesores
- Profesor debe impartir el nivel requerido
- Profesor debe impartir la categoría requerida
- El curso no debe haber iniciado

### Eliminaciones
- Profesores: no deben estar asignados
- Alumnos: no deben estar inscritos en cursos
- Cursos: sin alumnos o curso finalizado

---

# Excepciones Personalizadas

El sistema incluye excepciones propias para controlar errores comunes:

- DatosInvalidosException
- NoEncontradoException
- CapacidadLlenaException
- InscripcionException
- AsignacionProfesorException
- EliminacionNoPermitidaException
- FormatoInvalidoException

Cada una brinda información clara al usuario.

