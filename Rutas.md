# API Rutas - Backend Foro

## Usuarios Normales

### Posts
- **POST** `/api/posts` - Crear nuevo post
- **GET** `/api/posts` - Listar todos los posts
- **GET** `/api/posts/{postId}` - Obtener post por ID
- **GET** `/api/posts/autor/{autorId}` - Posts de un autor
- **GET** `/api/posts/buscar?titulo={titulo}` - Buscar posts por título
- **POST** `/api/posts/{postId}/like` - Dar like a un post
- **PUT** `/api/posts/{postId}` - Actualizar SU propio post (requiere header X-User-Id)
- **DELETE** `/api/posts/{postId}` - Eliminar SU propio post (requiere header X-User-Id)

### Comments
- **POST** `/api/comments` - Crear nuevo comentario
- **GET** `/api/comments` - Listar todos los comentarios
- **GET** `/api/comments/{commentId}` - Obtener comentario por ID
- **GET** `/api/comments/post/{postId}` - Comentarios de un post
- **GET** `/api/comments/autor/{autorId}` - Comentarios de un autor
- **GET** `/api/comments/post/{postId}/count` - Contar comentarios de un post
- **PUT** `/api/comments/{commentId}` - Actualizar SU propio comentario (requiere header X-User-Id)
- **DELETE** `/api/comments/{commentId}` - Eliminar SU propio comentario (requiere header X-User-Id)

## Administradores

### Posts (Admin puede modificar CUALQUIER post)
- **PUT** `/api/posts/{postId}` - Actualizar CUALQUIER post (requiere header X-User-Id)
- **DELETE** `/api/posts/{postId}` - Eliminar CUALQUIER post (requiere header X-User-Id)

### Comments (Admin puede modificar CUALQUIER comentario)
- **PUT** `/api/comments/{commentId}` - Actualizar CUALQUIER comentario (requiere header X-User-Id)
- **DELETE** `/api/comments/{commentId}` - Eliminar CUALQUIER comentario (requiere header X-User-Id)
- **DELETE** `/api/comments/{commentId}/con-permisos` - Eliminar con permisos extendidos (autor del post o admin)

## Headers Requeridos
- `X-User-Id` - ID del usuario para operaciones que requieren autenticación

## Body Examples

### Crear Post

{
  "titulo": "Título del post",
  "descripcion": "Contenido del post",
  "autorId": "ID del autor"
}


### Crear Comentario

{
  "postId": "ID del post",
  "contenido": "Contenido del comentario",
  "autorId": "ID del autor"
}