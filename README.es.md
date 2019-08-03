# Twitter War Bot
War bot multiplataforma para Twitter programado en Java, haciendo uso de la librería Twitter4j. Permite añadir intervalos de tiempo personalizados en los cuales el estado de la guerra podrá actualizarse.
Hay una versión ejecutable compilada para Windows (WarBotCompiled.zip) y el JAR para todas las plataformas.

![alt text](https://i.gyazo.com/0c8e64ab50349dbbad52095b701072b6.png)

## Características

* El bot te pedirá que especifiques el nombre de la guerra.
* El bot te pedirá que especifiques el intervalo de tiempo entre turnos (en segundos).
* Puedes especificar intervalos de tiempo en los que el bot actualizará el estado de la guerra, fuera de esos intervalos de tiempo, el bot no twitteará.
* Puedes especificar un mensaje personalizado para el pie del tweet del bot (o dejarlo vació).
* Puedes especificar frases personalizadas y añadir nuevas traducciones sin cambiar el código directamente.

## Uso

* Es requerido que tengas instalado Java para que el bot pueda funcionar: https://www.java.com/es/download/
* Tienes que poner tus propios tokens (claves de acceso) de Twitter en el archivo twitter4j.properties.
* El archivo "files/players.txt" contiene la lista de jugadores. El formato de este archivo es: "Nombre;Alias".
* El archivo "files/time-intervals.txt" contiene la lista de los intervalos de tiempo en los que el bot va a actualizar el estado. El formato de ese fichero es "10:00:10;23:59:59" (cada intervalo en una nueva linea y dejarlo en blanco para ejecutarlo todo en cualquier horario). NO poner intervalos tales como: "20:00:00;00:00:00" o "10:20:10;02:03:20".
* El archivo "files/config.txt" contiene la configuración global para el bot. El formato de este fichero es "nombrePropiedad=valor"

##
Adrián Fernández Arnal (@adrianfa5)<br />
Twitter: https://twitter.com/adrianfa5
