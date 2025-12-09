// Crear colecciones (MongoDB las crea automáticamente al insertar)
db.createCollection("posts")
db.createCollection("usuarios") 
db.createCollection("comments")

// Crear índices recomendados
db.posts.createIndex({ "autorId": 1 })
db.posts.createIndex({ "fechaCreacion": -1 })
db.comments.createIndex({ "postId": 1 })
db.usuarios.createIndex({ "username": 1 }, { unique: true })


// Si quieres usar esto directamente instala choco install mongodb-shell, si nop desde la terminal de mongo
// y usa mongosh "mongodb+srv://iggan443_db_user:Slayer185@foro.wywprcc.mongodb.net/Foro" "c:\Users\ggmen\Downloads\Foro\Backend-Foro\Backend-Foro\SciptsMongodb.js"
// igual no ocupar pq el servicio en línea ya jala ASDJASJDA, digo x emir