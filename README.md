# Microservicio Interview ms - Proyecto Terminal

Este sistema de creacion de entrevistas fue desarrollado como parte del Proyecto Terminal para la carrera de Ingeniería en Telemática. El objetivo es proporcionar un servicio 
para gestionar la manipulacion de entrevistar de usuarios relacionadas al desarrollo web en un entorno distribuido.

## Desarrolladores

- **Cabrera Vázquez Itzel Berenice**
- **Flores Chavarría Diego**

## Propiedad Intelectual

Este código fuente y todos los materiales relacionados están protegidos por las leyes de propiedad intelectual. Su uso, reproducción, distribución, modificación o venta sin la autorización expresa de los desarrolladores está estrictamente prohibido.

---

## Descripción del Proyecto

Este proyecto incluye las siguientes características clave:

- **Microservicios**: Implementación basada en Spring Boot y Spring Cloud.
- **Seguridad**: Uso de JWT (JSON Web Tokens) para la autenticación de usuarios.
- **Conexión a Base de Datos**: Integración con Microsoft SQL Server.
- **Gestión de Configuración**: Uso de archivos de configuración YAML para gestionar entornos y parámetros sensibles.
- **Balanceo de Carga**: Integración con un servidor Eureka para descubrimiento de servicios.
- **Logs Personalizados**: Los logs de la aplicación se almacenan diariamente en una carpeta específica para un seguimiento claro y organizado.

## Configuración del Proyecto

### Requisitos Previos

- **Java 17 o superior**
- **Maven 3.6+**
- **Microsoft SQL Server**
- **Docker (opcional, para despliegues containerizados)**

### Instalación y Configuración

1. **Clonar el Repositorio**  
   Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/ItzelCabrera/InterviewMS.git
   cd Interviewms
