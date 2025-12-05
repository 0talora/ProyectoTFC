## 📚 Índice
- [Guía de uso de la aplicación](#-guía-de-uso-de-la-aplicación)
- [Descripción del proyecto](#-descripción-del-proyecto)
- [Beneficios esperados](#-beneficios-esperados)
- [Objetivos](#-objetivos)
- [Planificación](#-planificación)
- [Análisis de requisitos](#-análisis-de-requisitos)
- [Diseño de la aplicación](#-diseño-de-la-aplicación)
- [Desarrollo de la aplicación](#-desarrollo-de-la-aplicación)
- [Requisitos para probar la aplicación](#️-requisitos-para-probar-la-aplicación)
- [Instalación de Docker y Docker Compose](#-instalación-de-docker-y-docker-compose)
- [Comandos útiles](#-comandos-útiles)


# 🪖 Reservas Airsoft

## 🧭 Guía de uso de la aplicación

Para comenzar a usar **ReservasAirsoft**, sigue estos pasos:

1. **Crea una cuenta nueva:**  
   Al abrir la aplicación, deberás registrarte introduciendo tu nombre, apellidos, nickname, correo electrónico y contraseña.

2. **Verificación por correo:**  
   Tras completar el registro, recibirás un **código de verificación** en el correo que hayas indicado.  
   Deberás introducir ese código en la aplicación para confirmar tu cuenta y poder iniciar sesión.

3. **Navegación por la aplicación:**  
   Una vez dentro, encontrarás una **barra superior** que contiene el menú principal.  
   Desde ahí podrás moverte entre las diferentes pantallas de la aplicación, como:
   - **Eventos disponibles**
   - **Mis reservas**
   - **Perfil de usuario**
   - *(y si eres administrador, también la gestión de eventos y campos)*

💡 *Recuerda que no podrás acceder a las funciones principales hasta haber verificado tu cuenta correctamente.*

---

### 🧑‍💼 Acceso como administrador

Por defecto, **todos los usuarios nuevos se crean como usuarios normales**.  
Si deseas probar las funciones de **administrador**, deberás modificar manualmente el rol en la base de datos:

1. Conéctate a la base de datos (por ejemplo, usando **DBeaver** o **MySQL Workbench**).  
2. Abre la tabla `usuarios`.  
3. Localiza tu usuario recién creado.  
4. Cambia el valor del campo `es_admin` a `1` (o `TRUE`).  
5. Guarda los cambios.  

Luego, al iniciar sesión con ese usuario, tendrás acceso al **panel de administración**, donde podrás:
- Crear, modificar y cancelar eventos.  
- Ver y gestionar los campos administrados.

> ⚠️ Este paso solo es necesario si quieres probar las funciones administrativas.  
> Los usuarios normales no tienen acceso a estas opciones.

---

## 📄 Descripción del proyecto
El proyecto consiste en el desarrollo de una aplicación de escritorio llamada **ReservasAirsoft**, cuyo objetivo principal es facilitar la gestión de reservas de campos de airsoft para usuarios y administradores. La aplicación está implementada en **Java** utilizando **Swing** para la interfaz gráfica, **BCrypt** para las contraseñas y **Hibernate** para la conexión y persistencia de datos en una base de datos remota alojada en **AWS RDS** (para la publicación de este proyecto se ha modificado la DDBB a una en local para facilitar la prueba de la aplicación).

El proyecto surge de la necesidad de digitalizar y facilitar la experiencia de reserva de los usuarios, así como la administración de eventos por parte de los responsables de los campos, siguiendo una arquitectura **MVC (Modelo-Vista-Controlador)** y un código modular y escalable.

---

## ✅ Beneficios esperados
- Digitalización y optimización del proceso de organización.
- Gestión de usuarios, reservas y disponibilidad en tiempo real.
- Base extensible para futuras mejoras: integración de pagos, rankings, estadísticas.

---

## 📝 Objetivos

### Objetivos generales
Desarrollar una aplicación que gestione usuarios, eventos y reservas, centralizando información y facilitando la interacción entre jugadores y organizadores.

### Objetivos específicos
- Analizar y documentar requisitos funcionales y no funcionales.
- Diseñar una interfaz clara e intuitiva para cada tipo de usuario (jugador, organizador, admin).
- Implementar autenticación segura con BCrypt.
- Desarrollar un sistema de reservas con control automático de plazas.
- Garantizar seguridad, modularidad y buenas prácticas de código (MVC, DAO).

---

### Conceptos clave
- **Hibernate:** ORM para Java.  
- **MVC:** Patrón que separa lógica, presentación y control.  
- **Swing:** Biblioteca gráfica de Java.  
- **DAO:** Patrón para encapsular acceso a datos.

---

## 🗂 Planificación
### Metodología
Se siguió una metodología incremental, dividiendo el proyecto en fases funcionales: registro/login, gestión de reservas, gestión de eventos y funcionalidades administrativas.

### Herramientas de seguimiento
Se utilizó **Notion** para el registro de tareas y control del progreso.

### Principales módulos desarrollados
- **Módulo de inicio de sesión:** Autenticación segura y gestión de sesión.  
- **Módulo de creación de cuenta:** Registro de usuarios con validación y encriptación de contraseñas.  
- **Módulo de usuario:** Visualización de eventos, reservas, perfil y cancelación.  
- **Módulo de campos:** Visualización de campos disponibles.  
- **Módulos administrativos:** Creación, actualización y cancelación de eventos; gestión de campos.

### Base de datos
- Diseño de entidad-relación (E/R) y creación de base de datos MySQL en **AWS RDS**.
- Garantiza disponibilidad, escalabilidad y rendimiento óptimo.

---

## 📊 Análisis de requisitos

### Requisitos funcionales
- Registro e inicio de sesión de usuarios.
- Gestión de campos y eventos.
- Control de reservas y número de plazas.
- Gestión administrativa y roles diferenciados.

### Requisitos no funcionales
- Seguridad: contraseñas encriptadas con BCrypt.  
- Rendimiento: consultas optimizadas con Hibernate.  
- Escalabilidad: base de datos en la nube.  
- Usabilidad: interfaz simple e intuitiva.  
- Mantenibilidad: código modular (MVC y DAO).  
- Disponibilidad: 99,9% del tiempo.

### Usuarios
- **Usuarios sin registrar:** registro.  
- **Usuarios registrados:** ver campos y eventos, reservar, cancelar y consultar historial.  
- **Administradores:** crear y cancelar eventos, gestionar campos, consultar reservas.

---

## 🖥 Diseño de la aplicación

### Arquitectura del sistema
- **MVC:** Separación de modelo, vista y controlador.  
- **DAO:** Separación de la lógica de negocio.  
- **Hibernate:** Persistencia de datos.  

---

## ⚙ Desarrollo de la aplicación

### Tecnologías utilizadas
- **Java 23**: backend.  
- **Swing**: frontend.  
- **Hibernate**: persistencia.  
- **Maven**: gestión de dependencias.  
- **AWS RDS (MySQL)**: base de datos en la nube.  
- **Git**: control de versiones.

### Funcionalidades principales
- **Gestión de sesión y autenticación:** con BCrypt y patrón Singleton.  
- **Creación de cuenta:** validación de correo, generación de código de verificación y encriptación de contraseña.  
- **Reservas de eventos:** control automático de plazas disponibles.  
- **Gestión de eventos:** creación, actualización y cancelación por administradores.  

## ⚙️ Requisitos para probar la aplicación

Antes de ejecutar **ReservasAirsoft**, asegúrate de cumplir con los siguientes requisitos técnicos y de entorno:

### 🧩 Requisitos del sistema
- **Sistema operativo:** Windows, macOS o Linux  
- **Java Development Kit (JDK):** versión **17 o superior** (recomendado **Java 23**)  
- **Maven:** instalado y configurado en el PATH del sistema  
- **Docker y Docker Compose:** para ejecutar la base de datos MySQL en contenedor  
- **Conexión a Internet:** necesaria para conectarse a la base de datos remota o descargar dependencias  

## 🧰 Instalación de Docker y Docker Compose

Para ejecutar este entorno necesitas tener instalado **Docker** (que incluye **Docker Compose** en sus versiones modernas).

### 🪟 Windows / 🍎 macOS

1. Descarga **Docker Desktop** desde la página oficial:  
   👉 [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)
2. Instálalo siguiendo el asistente.
3. Reinicia tu equipo si lo solicita.
4. Abre **Docker Desktop** y asegúrate de que esté en ejecución (icono de la ballena azul visible en la barra de tareas o menú superior).

> 💡 *Docker Desktop ya incluye Docker Compose, no es necesario instalarlo aparte.*

---

⚙️ Levantar el contenedor MySQL
Abre una terminal (o PowerShell en Windows) en la carpeta del proyecto, donde se encuentra el archivo docker-compose.yml, y ejecuta:

```bash
docker compose up -d
#Esto descargará la imagen oficial de MySQL 8.0 y creará un contenedor llamado AirsoftBDD con la base de datos Airsoft lista para usar.
```
🔍 Comprobar que el contenedor está corriendo
Ejecuta:
```bash
docker ps
```
Deberías ver algo similar a:

```bash

CONTAINER ID   NAME          IMAGE        STATUS          PORTS
abc1234def56   AirsoftBDD    mysql:8.0    Up 10 seconds   0.0.0.0:3306->3306/tcp
```
Si el contenedor aparece en estado Up, la base de datos está activa.

🧾 Cargar la base de datos (dos opciones)
Puedes inicializar los datos de la base de datos de dos formas:

🅰️ Opción 1 — Usar un gestor de bases de datos (opcional)
Herramientas recomendadas:

MySQL Workbench

DBeaver

Conéctate con estos datos:

| Campo       | Valor         |
|------------|---------------|
| **Host**       | localhost     |
| **Puerto**     | 3306          |
| **Usuario**    | usuario       |
| **Contraseña** | usuariopasswd |
| **Base**       | Airsoft       |


Una vez conectado, abre tu archivo ScriptBDD.sql y ejecútalo normalmente.

🅱️ Opción 2 — Ejecutar el script SQL desde la terminal (sin gestor)
Si no dispones de ningún gestor, ejecuta el script SQL directamente dentro del contenedor.

Asegúrate de que ScriptBDD.sql esté en la misma carpeta que docker-compose.yml.

🐧 Linux / 🍎 macOS
```bash
docker exec -i AirsoftBDD mysql -u usuario -usuariopasswd Airsoft < ScriptBDD.sql
```
🪟 Windows (PowerShell o CMD)
powershell
```bash
type ScriptBDD.sql | docker exec -i AirsoftBDD mysql -u usuario -usuariopasswd Airsoft
```

✅ Verificar que los datos se importaron correctamente
Conéctate al contenedor:

```bash
docker exec -it AirsoftBDD mysql -u usuario -p Airsoft
```
Cuando pida la contraseña, introduce:

```bash
usuariopasswd
```
Luego, dentro de MySQL, ejecuta:

sql
```bash
SHOW TABLES;
SELECT COUNT(*) FROM usuarios;
```
Si ves resultados, la importación fue exitosa 🎉

🚀 Probar la aplicación
Si todo ha sido correcto y los datos se cargaron sin errores, ya puedes abrir la aplicación y probarla.

## 🧹 Comandos útiles

Detener el contenedor	
```bash
docker compose down
```
Reiniciar la base de datos	
```bash
docker compose down -v && docker compose up -d
```
Ver logs del contenedor	
```bash 
docker logs -f AirsoftBDD
```


