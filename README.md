1: Para configurar o levantar el proyecto es necesario tener una instancia local de postgres bien
levantar el docker compose con el comando: docker-compose up --build estando ubicado en la raiz del proyecto (para esto es necesario tener instalado docker en la maquina)

2: listado de apis: path local = http://localhost:8080
- /products -> obtiene todos los productos GET
- /products -> crear un producto POST
  {
    "nombre":"Detodito",
    "descripcion":"picante",
    "precio":4000.5,
    "stock": 5
}
- /products/4 -> editar un producto PUT
  {
    "nombre":"Detodito editado",
    "descripcion":"picante",
    "precio":4000.5,
    "stock": 5
}
