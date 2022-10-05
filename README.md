# MOVIE CHALLENGE

Aplicación desarrollada para ingresar a la Aceleración de Alkemy.

La aplicación consume la API de https://www.themoviedb.org/ y presenta el listado de películas populares de forma paginada en el fragmento Home. Tiene desarrollado un sistema de placeholder para las imágenes, lo cual garantiza que la carga de las mismas desde la red no genere algun retardo en el deslizamiento por la pantalla o se interponga en la experiencia de usuario.

Además permite hacer una busqueda al tiempo que se escribe la consulta, pero sólo dentro de las películas que ya han sido visualizadas en el listado, requerimiento propio del Challenge planteado por Alkemy. 

Al seleccionar una película se navega a un fragmento de detalle, en el cual se pueden visualizar datos más específicos del film. En esta pantalla una animacion con efecto Parallax nos mantiene la información más reelevante en pantalla aún cuando scrolleamos. 

Cada vez que el usuario ingresa al fragmento de detalle, la aplicación primero busca el detalle en una base de datos local, en caso de no contenter dicha información, la almacena en el dispositvo utilizando la librería Room para la próxima vez que sea visitada.

Al volver al fragmento principal, se recuperan los datos del estado de la pantalla anterior, asi sean de busqueda o se trate de la posición en el listado.

Tecnologías utilizadas:
- Arquitectura MVVM
- Jetpack Compose
- Coroutines
- Retrofit
- Paging
- Dagger Hilt
- Navigation
- Glide
- AndroidX
- Material Design

# Video experiencia de usuario:
https://youtu.be/JY4lnErk6I4

# PANTALLA PRINCIPAL: LISTADO
![Screenshot_20221005-104704_Movie Challenge](https://user-images.githubusercontent.com/100050838/194088409-177cce90-d20f-41dc-84bc-fd32d97fd443.jpg)

# PANTALLA DETALLE: ANTES DEL SCROLL
![Screenshot_20221005-104803_Movie Challenge](https://user-images.githubusercontent.com/100050838/194088419-8d0e2b36-bf37-43cf-9887-4c1d7937b45b.jpg)

# PANTALLA DETALLES DESPUÉS DEL SCROLL
![Screenshot_20221005-104815_Movie Challenge](https://user-images.githubusercontent.com/100050838/194088423-f7b6130b-ef1f-439c-90a4-e296817a7f51.jpg)

# PANTALLA PRINCIPAL: BUSQUEDA EN CURSO 
![Screenshot_20221005-104735_Movie Challenge](https://user-images.githubusercontent.com/100050838/194088418-591f7d85-6aa3-41a6-b50a-077713877c07.jpg)
