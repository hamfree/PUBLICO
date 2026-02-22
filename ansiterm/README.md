# Descripción de la librería ANSITerm

La librería ANSITerm es una biblioteca Java que proporciona funcionalidades para trabajar con secuencias de escape ANSI en la consola. Permite controlar el 
comportamiento del terminal, como el color de texto, el fondo, el tamaño de la ventana, el cursor, entre otros.

Es una librería sencilla, con un conjunto limitado de funcionalidades, pero que puede ser útil para aquellos que necesitan controlar el terminal desde Java.

Funciona razonablemente bien en aquellas emulaciones de Terminal que aceptan las secuencias de escape ANSI del terminal VT100, VT220, xterm, etc. Ejemplos 
de emulaciones de terminal que soportan secuencias ANSI son: PuTTY, Windows Terminal, iTerm2, etc.

La clase `App` es la clase de inicio de la aplicación y se usa como punto de entrada principal para ejecutar las demostraciones y pruebas de la librería.
Está acompañada de una clase de utilidad `Util` que proporciona métodos de utilidad para la librería y clases de ayuda que implementan, cada una, una de las 
opciones del menú que muestra por pantalla la clase `App`.

Para algunas funcionalidades de más 'bajo nivel' que no pueden ser controladas por las secuencias de escape ANSI, tanto en Windows, como en Linux, se ha 
recurrido a la librería JNA (para acceder a las API nativas del sistema operativo Windows) y al comando stty, presente en la mayoría de las distribuciones 
Linux, que permite gestionar la configuración del terminal a muy bajo nivel.

La Librería ANSITerm se ha ideado para que pueda ejecutarse tanto en Windows como en Linux, aunque se ha hecho con mayor atención a la compatibilidad con 
Windows, ya que es el sistema operativo sobre el que se ha desarrollado y probado la librería.

Las pruebas se han realizado utilizando la aplicación *Windows Terminal* y para Linux, el terminal que viene incluido en el software *Git for Windows*, aunque
algunas pruebas se han hecho en una distribución Linux Ubuntu instalada físicamente en el ordenador.

Tienes más información sobre las secuencias ANSI, los terminales, y su tratamiento en Windows y Linux  en los siguientes enlaces:
- [Wikipedia - ANSI escape code](https://en.wikipedia.org/wiki/ANSI_escape_code)
- [Wikipedia - VT100](https://en.wikipedia.org/wiki/VT100)
- [Wikipedia - xterm](https://en.wikipedia.org/wiki/Xterm)
- [Wikipedia - Windows Terminal](https://en.wikipedia.org/wiki/Windows_Terminal)
- [Wikipedia - stty](https://en.wikipedia.org/wiki/Stty)