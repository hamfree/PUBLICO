# Finalizando Scanner cuando se completa la entrada en Java

- Última actualización: **4 de Diciembre de 2025**
- Escrito por: **Sidrah Abdullah**
- Revisado por: **Hiks Geranov**
- Traducido por: **Juan F. Ruiz**
- URL original: https://www.baeldung.com/java-scanner-stop-on-complete-input


## 1. Introducción

Cuando se trabaja con programas de Java que leen la entrada desde la consola, 
usamos la clase `Scanner`. Es simple, poderosa, y lo bastante flexible para 
manejar varios tipos de entrada, incluyendo enteros, dobles, y cadenas. Sin 
embargo, uno de los mayores retos que afrontan los desarrolladores cuando 
trabajan con un objeto `Scanner` es averiguar como parar de leer la entrada una 
vez se completa la tarea.

Consideremos una situación donde queremos leer múltiples líneas de entrada, 
procesarlas, y después terminar el programa de forma adecuada. En un primer 
momento, podría parecer que presionar la tecla _Retorno_ unas pocas veces 
señalaría al programa que la entrada ha terminado. Desafortunadamente, el 
programa continúa esperando más entradas, y no sabe por sí mismo cuando hemos 
terminado de escribir.

En este tutorial exploraremos cómo terminar un `Scanner` de Java después de que 
se termine la entrada. Primero, describiremos diferentes estrategias para 
terminar un `Scanner`, y veremos el código de los ejemplos que demuestran las 
aproximaciones correctas. Además, examinaremos porqué ciertos intentos básicos 
pueden fallar. Por último, concluiremos con las mejores prácticas para escribir 
código de manejo de entradas limpio en Java.

## 2. Terminar la Clase Scanner

Un `Scanner` conectado a `System.in` espera continuamente por entradas. Desde la 
perspectiva de la codificación, mientras el usuario no haya dicho explícitamente 
que pare, asume que más entradas pueden llegar. Presionar la tecla _Retorno_ 
simplemente indica el final de una línea, no el final del flujo de entrada 
entero.

Por ejemplo, consideremos un caso simple de terminación de `Scanner`:

```java
import java.util.Scanner;
public class SampleScanner {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();
                System.out.println(line);
            }
        } finally {
           scan.close();
        }
    }
}
```

A primera vista, la lógica parece simple. El bucle continúa leyendo líneas y las 
imprime hasta que no haya más líneas disponibles. Sin embargo, a menos que el 
programa reciba una señal explícita de _final-de-entrada_, `hasNextLine()` se 
mantiene devolviendo verdadero. Eso es por lo que el bucle no termina 
automáticamente cuando el usuario simplemente presiona _Retorno_ varias veces.

## 3. Consideraciones de la terminación en Scanner

En particular, se debe tener cuidado al utilizar `Scanner` en términos de su 
finalización.

### 3.1. Cerrando la clase Scanner

Cuando se trabaja con `Scanner`, es importante cerrar el recurso para evitar 
fugas de memoria. Sin embargo, si `Scanner` está leyendo de `System.in`, 
cerrarlo también cierra el flujo de entrada subyacente, que no podrá ser 
reabierto.

Esto puede provocar más errores.

### 3.2. Terminación elegante vs. Salida forzada

Algunos ejemplos muestran el uso de `System.exit(0)` para terminar 
inmediatamente. A pesar de que esto funciona, es una herramienta basta. Un 
enfoque más limpio es salir del bucle, permitiendo que el programa finalice 
naturalmente.

Esto es especialmente importante en multitarea.

## 4. Uso erróneo del operador == para comprobar la entrada

Si intentamos comparar la entrada directamente usando `==` o comprobando si la 
entrada es igual a una cadena vacía para terminar la entrada de `Scanner`, 
generalmente no funcionará. Una excepción es apuntar específicamente a cadenas 
vacías.

Por ejemplo, consideremos la comparación de scan.nextLine en 
`SampleScannerScan.java`:

```java
import java.util.Scanner;
public class SampleScannerScan {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line == null) {
                    System.out.println("Saliendo del programa (comprobacion de nulo)...");
                    System.exit(0);
                }
                System.out.println("La entrada fue: " + line);
            }
        } finally {
            scan.close();
        }
    }
}
```

El operador `==` comprueba si dos referencias **apuntan al mismo objeto**, no si 
los contenidos de las dos cadenas son iguales. Por lo tanto, incluso si dos 
cadenas contienen los mismos caracteres, `==` puede devolver falso. Para 
comparar cadenas apropiadamente, necesitamos usar `.equals()` o `.isEmpty()`.

## 5. Uso del marcador Final-de-fichero (EOF)

El enfoque más directo para cerrar `Scanner` en realidad es permitir al usuario 
que indique el final-de-entrada usando el marcador de **EOF** del sistema 
operativo. En sistemas del tipo Unix, esto es un carácter de control indicado 
por **CTRL+D**, mientras que Windows usa **CTRL+Z**. Cuando se envía el marcador 
de **EOF**, `hasNextLine()` devuelve falso, y el bucle se termina naturalmente.

Veamos el código de ejemplo en `EOFExample.java` que demuestra el uso de **EOF**:

```java
import java.util.Scanner;
public class EOFExample {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("Introduzca texto (presioe CTRL+D en Unix/Mac o CTRL+Z en Windows para terminar):");
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println("Usted introducjo: " + line);
            }
            System.out.println("Final de entrada detectado. Programa terminado.");
        } finally {
            scan.close();
        }
    }
}
```

Este enfoque funciona sin modificar el código, pero se basa en el conocimiento 
del usuario de cómo enviar la señal de **EOF**. Sin embargo, puede que no 
siempre sea la opción más amigable para el usuario.

## 5. Usar un valor centinela

Otro método común para terminar `Scanner` de forma correcta es definir una 
palabra clave especial que indica que el usuario ha terminado. Este valor 
centinela podría ser algo simple como salir, abandonar, o incluso un símbolo tal 
como `##`. El programa puede comprobar cada línea, y cuando coincida con el 
valor centinela, termina de forma correcta.

Modificamos `SampleScannerSentinel.java` para comprobar la terminación en `Scanner`:

```java
import java.util.Scanner;
public class SampleScannerSentinel {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();
                if (line.equals("salir")) {
                    System.out.println("Saliendo del programa...");
                    break;
                }
                System.out.println(line);
            }
        } finally {
            scan.close();
        }
    }
}
```

En esta versión, teclear `salir` termina inmediatamente el bucle. Este enfoque 
es más fácil, ya que el usuario sabe exactamente como señalar la finalización.

## 6. Usar un bucle do-while

Otra elección para completar `Scanner` es utilizar un bucle **do-while**. Esta 
estructura asegura que al menos se lee una línea antes de que se compruebe la 
condición de terminación. Veamos el código del fichero `SampleScanner.java`, 
pero con un blucle do-while (`DoWhileScanner.java`):

```java
import java.util.Scanner;
public class DoWhileScanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        do {
            input = sc.nextLine();
            System.out.println(input);
        } while (!input.equals("salir"));
        sc.close();
    }
}
```

En este ejemplo, el programa se repite hasta que se escribe la palabra clave 
`salir`. Es un proceso limpio y directo que garantiza el procesamiento inmediato 
de la entrada del usuario.

## 7. Conclusión

En este artículo, cubrimos cóm terminar `Scanner` cuando la entrada se completa 
en Java. Los dos enfoques más efectivos o emplean el marcador del sistema EOF o 
se define un valor centinela de salida. Mientras que el marcado EOF es 
eficiente, el método del centinela a menudo ofrecen una experiencia más clara 
para los usuarios. Además, también aprendimos por qué soluciones ingenuas como 
verificar valores nulos o usar == fallan.

_Nota del traductor_: Esta es una traducción libre de un artículo del sitio web 
de [Baeldung](https://www.baeldung.com/), donde encontrarás cientos, o incluso 
miles de artículos sobre Java y el marco de trabajo de Spring Framework y 
también cursos completos. He incluído el enlace al artículo original al 
principio de este documento en su web. 

Si existe alguna discrepancia, por favor, remítase al artículo original.