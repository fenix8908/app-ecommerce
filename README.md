1: Para configurar o levantar el proyecto es necesario tener una instancia local de postgres bien
levantar el docker compose con el comando: docker-compose up --build estando ubicado en la raiz del proyecto (para esto es necesario tener instalado docker en la maquina)

2: listado de end-points: path local = http://localhost:8080
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
- /products/3 -> Elemina un producto DELETE
- /users/2 ->  Buscar usuario por id GET
- /users -> Crear usuario POST
  {
    "nombre":"George",
    "correo":"prueba@gmail.com",
    "direccion":"calle real 123"

}
- /orders/2 -> Buscar orden por id GET
-  /orders/2 -> Crear pedido
{ 
  "items": [
  { 
      "producto": {     
        "id":5,
        "nombre": "portatil",
        "descripcion": "dell",
        "precio": 3000.5,
        "stock": 5
      },
      "orden": {
        "estado":"EN_PROCESO"
      },
      "cantidad": 1
    }
  ]
}
