# Servidor Web hecho en Java

Servidor web que soporta múlltiples solicitudes seguidas no concurrentes. El servidor lee los archivos del disco local y retorna todos los archivos solicitados, incluyendo páginas html, archivos java script, css e imágenes.
Incluye en la aplicación la comunicación asíncrona con unos servicios REST, NO se usan frameworks web como Spark o Spring, solo se uso Java y las librerías para manejo de la red.

## Autor

* **Nicolas Bernal** - Autor y Desarrollador del Proyecto

### Paso a paso para la instalacion, compilacion y ejecucion del proyecto

Clona este repositorio y navega al directorio donde clonaste el proyecto:

```
git clone https://github.com/NicoBernal19/Taller2AREP.git
```

Construte y tambien compila el proyecto con Maven:

```
mvn clean install
```

Ejecuta la aplicacion, corre la clase principal para iniciar el servidor:

```
mvn exec:java -Dexec.mainClass="co.edu.eci.arep.WebFramework"
```

El servidor esta hecho para que inicie en el puerto `35000`, una vez ejecutado, ya esta listo para que puedas usarlo y probarlo.

## Funcionalidades del servidor

El código está estructurado en diferentes archivos para no tener una sola clase llena de codigo, ademas de que es una buena practica y permite que todo este mas ordenado y compacto, hace que sea mas facil extender a posterior el codigo.

Entre las funcionalidades que se ofrecen encontramos las siguientes:

- Soporta los métodos `GET` y `POST`.
- Permite servir HTML, CSS, JS e imágenes.
- Hace uso de funciones Lambda.

## Pruebas

### Pruebas Unitarias

Antes que nada debo dejar claro que hay algunas pruebas que requieren que el servidor este corriendo para que funcionen, por lo tanto sugiero ejecutar primero el servidor y luego si ejecutar las pruebas.

Para poder ejecutar las pruebas con las que cuenta este proyecto ejecuta el siguiente comando:

```
mvn test
```

A continuacion se encuentran imagenes de la ejecucion de las pruebas:

![imagen](src/main/resources/images/1.png)

![imagen](src/main/resources/images/2.png)

![imagen](src/main/resources/images/3.png)

![imagen](src/main/resources/images/4.png)

### Pruebas en tiempo real

Abre el navegador para poder probar el servidor y accede al siguiente enlace:

```
http://localhost:35000
```

Una vez abierto el servidor web puedes navegar la pagina, probando las distintas funcionalidades. A continuacion encontramos algunos ejemplos de acciones que se pueden hacer:

![imagen](src/main/resources/images/5.png)

![imagen](src/main/resources/images/6.png)


### Pruebas archivos estáticos

Prueba a acceder a los siguientes archivos:

```
http://localhost:35000/index.html
http://localhost:35000/static/script.js
http://localhost:35000/static/styles.css
http://localhost:35000/images/realMadrid.png
```
