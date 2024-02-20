# Proyecto API RESTful de Gestión de Usuarios
Esta aplicación implementa una API RESTful para la gestión de usuarios. La API proporciona endpoints para el registro, modificación, listado de usuarios y generación de tokens de acceso al iniciar sesión o registrarse para realizar operaciones definidas.

## Herramientas
- Java 8 o superior
- H2
- Maven
- Spring
- Git
- Lombok
- JWT

## Instalación: 

Siga estos pasos para instalar y ejecutar el proyecto:
- Clonar el repositorio: git clone https://github.com/sainara200/JavaPruebas.git
- Importar el proyecto en Spring como un Maven Project
- Click derecho en el proyecto importado, seleccionar Maven>Update Project para descargar las  dependencias.
- Ejecutar con click derecho Run As>Spring Boot App

## Rutas:
- http://localhost:3001/h2-ui -----Base de datos H2
- **POST** localhost:3001/users/registro
- **POST** localhost:3001/users/login
- **GET** localhost:3001/auth/listar
- **PUT** localhost:3001/auth/modificar/{username}



## Endpoints

### Registrar Usuario
- **URL:** localhost:3001/users/registro
- **Método HTTP:** POST
- **Descripción:** Registra un nuevo usuario en el sistema y retorna token de acceso/operación.

**Cuerpo de la Solicitud (JSON):**
```json
{
    "name": "Pedro Pérez",
    "username": "rosico@perez.nkK",
    "password": "Perez2023@",
    "phones": [
        {
            "number": "5554443",
            "citycode": "3",
            "countrycode": "57"
        }
    ]
}
```
**Consideraciones:**
- El formato del correo debe ser el correcto: example@exam.com
- El formato de la contraseña debe contener como mínimo 8 caracteres, una mayúscula, una minúscula y un número: testeo123S
- Si el username ingresado se encuentra en la base de datos vinculado a un usuario, no se permitirá el registro.

**Responses**
- **Respuesta exitosa (HTTP 201):**
  
Retorna datos específicos del usuario recién creado con un token de acceso.

- **Respuesta Fallo (HTTP 400):**

Mensaje de error con formato:
```json
{
  "mensaje": "El formato del correo electrónico no es válido"
}
```

- **Respuesta Fallo (HTTP 409):**

Mensaje de error con formato:
```json
{
  "mensaje": "El correo rosico@perez.com ya ha sido registrado para otro usuario"
}
```


### Login Usuario

- **URL:** localhost:3001/users/login
- **Método HTTP:** POST
- **Descripción:** Valida datos de un usuario registrado en el sistema y retorna token de acceso/operación.

**Cuerpo de la Solicitud (JSON):**
```json
   {
    "username": "pedro@perez.nekkK",
    "password": "Perez2023@"
   }
```
**Responses**
- **Respuesta Exitosa (HTTP 200):**

Retorna token de acceso con formato:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9"
}
```

- **Respuesta Fallo (HTTP 400):**

Mensaje de error con formato:
```json
{
    "mensaje": "El usuario no existe"
}
```
- **Respuesta Fallo (HTTP 500):** 

Mensaje de error con formato:
```json 
{
    "mensaje": "Error al generar el token: Bad credentials"
}
```


### Modificar Usuario
- **URL:** localhost:3001/auth/modificar/{username}
- **Método HTTP:** PUT
- **Authorization:** Bearer Token
- **Descripción:** Modifica los datos de un usuario existente.
- **Parámetro de ruta:** username - El correo electrónico del usuario a modificar.

**Cuerpo de la Solicitud (JSON):**
```json
{
"name": "rosita",
"username": "rosita@rodriguez.com",
"password": "justino123@S",
"phones": [
        {
            "number": 88888,
            "citycode": "3",
            "countrycode": "57"
        },
         {
            "number": 666,
            "citycode": "3",
            "countrycode": "55"
        }
    ]
}
```

**Consideraciones:**
- El formato del correo debe ser el correcto: example@exam.com
- El formato de la contraseña debe contener como mínimo 8 caracteres, una mayúscula, una minúscula y un número: testeo123S
- En caso de que la solicitud no tenga atributos con valor, persistirán los datos de la última actualización en la base de datos.
- Si el username ingresado se encuentra en la base de datos vinculado a un usuario, no se permitirá el registro.
- Se valida el bearer token, en caso de no proporcionarlo, la solicitud será marcada como forbidden

**Responses**
- **Respuesta Exitosa (HTTP 200):**

Retorna el usuario modificado.

- **Respuesta Fallo (HTTP 400):**

Mensaje de error con formato:
```json
{
     "mensaje": "El formato del correo electrónico no es válido"
}
```
```json
{
    "mensaje": "El usuario no existe"
}
```

- **Respuesta Fallo (HTTP 409):**

Mensaje de error con formato:
```json
{
  "mensaje": "El correo rosico@perez.com ya ha sido registrado para otro usuario"
}
```

- **Respuesta Fallo (HTTP 403):**

Bearer Token no autorizado o no proporcionado.



### Listado de Usuarios
- **URL:** localhost:3001/auth/listar
- **Método HTTP:** GET
- **Authorization:** Bearer Token
- **Descripción:** Obtiene la lista de todos los usuarios registrados en el sistema.

**Responses**
- **Respuesta Exitosa (HTTP 200):**

Retorna una lista de usuarios.


- **Respuesta Fallo (HTTP 403):**

Bearer Token no autorizado o no proporcionado.

