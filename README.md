# Backend Foro - API REST

## Despliegue en la Nube

### Render 

1. Crear cuenta en render.com
2. Conectar repositorio GitHub
3. agrega las variables de entorno (las aplication.properties) se pueden agregar más fáciles si lo agregas como "import from .env"
4. Espera y despliega tu último commit, después cada vez que hagas un push y lo sincronices con la nube, se va a actualizar automáticamente
    Con tu último commit :D
5. Ahora ves a "Events" y copia el enlace de hasta arriba, dirá algo como https://backend-foro-fcn0.onrender.com
6. Agrega esa URL como parámetro para tu frontend
7. Reza para que funcione 

## Ejecución Local

### Prerrequisitos
- Docker Desktop
- Git

### Pasos
# 1. Clonar repositorio
git clone <tu-repo-url>
cd Backend-Foro/Backend-Foro

# 2. Configurar variables de entorno
Editar application.properties con tu conexión MongoDB

# 3. Ejecutar
docker-compose up --build

# 4. Verificar
curl http://localhost:8080/api/posts

## Ejemplos de Peticiones

### Producción (Render)
URL Base: `https://backend-foro-fcn0.onrender.com`

### Local (Desarrollo)

### Autenticación

#### Registro de Usuario
**CURL:**
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"123","email":"test@test.com"}'

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/auth/register" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"username":"test","password":"123","email":"test@test.com"}'

#### Login
**CURL:**
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"123"}'

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"email":"test@test.com","password":"123"}'

### Posts CRUD

#### Crear Post
**CURL:**
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -H "X-User-Id: TU_USER_ID" \
  -d '{"titulo":"Mi Post","descripcion":"Contenido del post","autorId":"TU_USER_ID"}'

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/posts" `
  -Method POST `
  -ContentType "application/json" `
  -Headers @{"X-User-Id"="TU_USER_ID"} `
  -Body '{"titulo":"Mi Post","descripcion":"Contenido del post","autorId":"TU_USER_ID"}'

#### Obtener Todos los Posts
**CURL:**
curl -X GET http://localhost:8080/api/posts

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/posts" -Method GET

#### Obtener Post por ID
**CURL:**
curl -X GET http://localhost:8080/api/posts/POST_ID

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/posts/POST_ID" -Method GET

#### Actualizar Post
**CURL:**
curl -X PUT http://localhost:8080/api/posts/POST_ID \
  -H "Content-Type: application/json" \
  -H "X-User-Id: TU_USER_ID" \
  -d '{"titulo":"Post Actualizado","descripcion":"Nuevo contenido"}'

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/posts/POST_ID" `
  -Method PUT `
  -ContentType "application/json" `
  -Headers @{"X-User-Id"="TU_USER_ID"} `
  -Body '{"titulo":"Post Actualizado","descripcion":"Nuevo contenido"}'

#### Eliminar Post
**CURL:**
curl -X DELETE http://localhost:8080/api/posts/POST_ID \
  -H "X-User-Id: TU_USER_ID"

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/posts/POST_ID" `
  -Method DELETE `
  -Headers @{"X-User-Id"="TU_USER_ID"}

### Comments CRUD

#### Crear Comentario
**CURL:**
curl -X POST http://localhost:8080/api/comments \
  -H "Content-Type: application/json" \
  -H "X-User-Id: TU_USER_ID" \
  -d '{"postId":"POST_ID","contenido":"Mi comentario","autorId":"TU_USER_ID"}'

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/comments" `
  -Method POST `
  -ContentType "application/json" `
  -Headers @{"X-User-Id"="TU_USER_ID"} `
  -Body '{"postId":"POST_ID","contenido":"Mi comentario","autorId":"TU_USER_ID"}'

#### Obtener Comentarios de un Post
**CURL:**
curl -X GET http://localhost:8080/api/comments/post/POST_ID

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/comments/post/POST_ID" -Method GET

#### Actualizar Comentario
**CURL:**
curl -X PUT http://localhost:8080/api/comments/COMMENT_ID \
  -H "Content-Type: application/json" \
  -H "X-User-Id: TU_USER_ID" \
  -d '{"contenido":"Comentario actualizado"}'

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/comments/COMMENT_ID" `
  -Method PUT `
  -ContentType "application/json" `
  -Headers @{"X-User-Id"="TU_USER_ID"} `
  -Body '{"contenido":"Comentario actualizado"}'

#### Eliminar Comentario
**CURL:**
curl -X DELETE http://localhost:8080/api/comments/COMMENT_ID \
  -H "X-User-Id: TU_USER_ID"

**PowerShell:**
Invoke-WebRequest -Uri "http://localhost:8080/api/comments/COMMENT_ID" `
  -Method DELETE `
  -Headers @{"X-User-Id"="TU_USER_ID"}

## Notas

- **X-User-Id**: Header requerido para operaciones de modificación/eliminación
- **TU_USER_ID**: Reemplazar con ID real de usuario obtenido del login (aunque tambien por algún administrador)
- **POST_ID/COMMENT_ID**: Reemplazar con IDs reales de posts/comentarios

