# Utiles

Esta librería contiene utilidades para el desarrollo de aplicaciones Java.

Dispone de varias clases como `Menu`, `MenuManager`, `Util` y `TestUtils` que 
pueden ser utilizadas para facilitar el desarrollo de aplicaciones Java.

- Menu: Esta clase permite crear y gestionar menús interactivos en la consola.
- MenuManager: Esta clase permite crear y gestionar menús interactivos en la consola.
- Util: Esta clase es una clase de utilidad que contiene varios métodos estáticos para la recuperación de información del sistema, procesamiento de colecciones, registro (logging) y control de flujo del programa. Esta clase está diseñada para evitar su instanciación.
- TestUtils: Esta clase contiene métodos de utilidad para el desarrollo de aplicaciones Java.

## Licencia

Esta librería está licenciada bajo la Licencia GNU General Public License v3.0. Consulta el archivo [LICENSE](https://www.gnu.org/licenses/gpl-3.0.html) para obtener más detalles.

## Por hacer

- El menú, cuando se introduce un valor incorrecto, primero navega a la última opción válida y da el error cuando vuelve de la opción. Debería dar el error inmediatamente después de que el usuario pulse <INTRO>.
- Hacer más test a las clases `Menu` y `MenuManager`.
- Incluir un '_borrado_' de pantalla _a lo cutre_ (imprimiendo tantos saltos de líneas como líneas tiene el buffer de pantalla). ¿Cómo saber el número de líneas que tiene la consola? Investigarlo (**_no utilizar la librería ANSITerm_**)