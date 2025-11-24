=====================================================
README.txt - Práctica 5. Herencia y Excepciones
=====================================================

## 1. Objetivo del Programa

Este programa simula un sistema de reservas de vuelos para una Agencia Aeroespacial (Vuelos Internacionales, Estratosféricos e Interplanetarios). El objetivo principal es demostrar el uso de:
1.  **Herencia** (Vuelo -> Vuelo Internacional -> Vuelo Estratosférico / Vuelo Interplanetario).
2.  **Manejo de Excepciones** (usando VueloException, CertificadoException, etc.) para crear una interfaz de usuario robusta donde los errores son reportados instantáneamente.

## 2. Datos Precargados

Al iniciar el programa (ejecutando MainAgencia), se cargan automáticamente los siguientes datos en el sistema (AgenciaDeViajes):

### Pasajeros (Pasaporte)
| ID | Nombre | Apellido | Edad | Vencimiento ID |
| 
| **ID001** | Juan | Perez | 30 | (5 años después) |
| **ID002** | Ana | Lopez | 25 | (5 años después) |
| **ID003** | Lara | Gomez | 40 | (5 años después) |

### Certificados de Salud (CS)
| ID Pasaporte | Tipo | Clave Salud | Fecha Certificado |
| 
| ID001 | **E** (Estratosférico) | Apto (A) | 2100-01-01 |
| ID001 | **P** (Interplanetario) | Apto (A) | 2100-01-02 |
| ID002 | **E** (Estratosférico) | Apto (A) | 2100-05-05 |
| ID003 | P (Interplanetario) | **No Apto (NA)** | 2000-01-01 (Viejo) |

## 3. Pasos de Prueba (Demostración de Funcionalidad)

Utiliza el menú principal (opciones 3, 4 y 5) para probar los casos de éxito, errores de validación y errores de requisitos.
