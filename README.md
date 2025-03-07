# ApiBoostly

## Descripción
ApiBoostly es una API desarrollada con Spring Boot para la gestión de usuarios, proyectos y categorías. Proporciona endpoints para la administración de usuarios, autenticación, gestión de roles y manejo de proyectos y categorías.

## Tecnologías Utilizadas
- **Java 17**
- **Spring Boot** (para el desarrollo de la API)
- **Spring Security** (para la autenticación y autorización)
- **JPA/Hibernate** (para la persistencia de datos)
- **Maven** (para la gestión de dependencias)
- **Base de Datos**: MySQL/PostgreSQL (según configuración)

## Instalación y Configuración
### Prerrequisitos
- Tener instalado Java 17 o superior.
- Tener instalado Maven.
- Tener una base de datos configurada (MySQL o PostgreSQL).

### Pasos para ejecutar el proyecto
1. Clonar el repositorio:
   ```sh
   git clone https://github.com/usuario/ApiBoostly.git
   cd ApiBoostly
   ```
2. Configurar el archivo `application.properties` en `src/main/resources/` con los datos de conexión a la base de datos.
3. Construir el proyecto con Maven:
   ```sh
   mvn clean install
   ```
4. Ejecutar la aplicación:
   ```sh
   mvn spring-boot:run
   ```

## Endpoints Principales

### Categorías
- `GET /api/categorias` - Lista todas las categorías.
- `GET /api/categorias/{id}` - Obtiene una categoría por ID.

### Proyectos
- `GET /api/proyectos` - Lista los proyectos.
- `GET /api/proyectos/usuario/{idUsuario}` - Obtiene los proyectos de un usuario específico.
- `GET /api/proyectos/{id}` - Obtiene un proyecto específico.
- `GET /api/proyectos/categoria/{idCategoria}` - Obtiene los proyectos de una categoría específica.
- `POST /api/proyectos/{id}` - Crea un nuevo proyecto.
- `PUT /api/proyectos/{id}` - Actualiza un proyecto.
- `DELETE /api/proyectos/{id}` - Elimina un proyecto.

### Roles
- `GET /api/roles` - Lista todos los roles.
- `GET /api/roles/{id}` - Obtiene un rol por ID.
- `DELETE /api/roles/{id}` - Elimina un rol.

### Usuarios
- `GET /api/usuarios` - Obtiene la lista de usuarios.
- `GET /api/usuarios/{id}` - Obtiene un usuario por ID.
- `PUT /api/usuarios/{id}` - Actualiza un usuario.
- `GET /api/usuarios/email/{email}` - Obtiene un usuario por su email.
- `POST /api/usuarios/generar-token` - Genera un token para recuperación de contraseña.
- `POST /api/usuarios/reset-password` - Restablece la contraseña de un usuario.

## Seguridad
El sistema utiliza Spring Security con JWT para la autenticación. Cada solicitud debe incluir el token JWT en la cabecera `Authorization`:
```sh
Authorization: Bearer {token}
```

## Estructura del Proyecto
```
ApiBoostly/
├── src/
│   ├── main/
│   │   ├── java/cliper/apiBoostly/
│   │   │   ├── controladores/  # Controladores REST
│   │   │   ├── daos/           # Entidades JPA
│   │   │   ├── dtos/           # Data Transfer Objects
│   │   │   ├── repository/     # Repositorios JPA
│   │   │   ├── servicios/      # Metodos manejo de datos
│   │   ├── resources/
│   │   │   ├── application.properties  # Configuración
│   ├── test/
│   │   ├── java/cliper/apiBoostly/      # Pruebas unitarias
└── pom.xml  # Dependencias de Maven
```

