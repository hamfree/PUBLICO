# Revisión de la base de código y propuesta de tareas

## 1) Tarea para corregir un error tipográfico

**Problema detectado**
- La clase y el archivo `EraseSecuencesCodes` usan la palabra `Secuences`, que está mal escrita en inglés; la forma correcta es `Sequences`.

**Tarea propuesta**
- Renombrar el archivo `EraseSecuencesCodes.java` a `EraseSequencesCodes.java`.
- Renombrar la clase pública a `EraseSequencesCodes`.
- Actualizar todas las importaciones y referencias internas para mantener compatibilidad de compilación.

**Criterio de aceptación**
- El proyecto compila sin errores por referencias rotas.
- No quedan apariciones de `EraseSecuences` en el código (salvo en historial Git).

---

## 2) Tarea para solucionar un fallo

**Problema detectado**
- `EraseSecuencesCodes` define constructor privado (patrón de clase utilitaria), pero sus métodos `deleteChar()` y `deleteChars(int)` no son estáticos.
- Esto hace que esos métodos no sean invocables desde fuera de la clase (no se puede instanciar), lo cual es un fallo de API.
- Además, el JavaDoc de `deleteChars(int)` indica que el parámetro debe ser positivo, pero no hay validación.

**Tarea propuesta**
- Convertir `deleteChar()` y `deleteChars(int)` a métodos `static`.
- Añadir validación explícita en `deleteChars(int)` para rechazar valores no positivos con `IllegalArgumentException`.

**Criterio de aceptación**
- `deleteChar()` y `deleteChars(int)` se pueden usar como métodos de utilidad estáticos.
- `deleteChars(0)` y `deleteChars(-1)` lanzan excepción documentada.

---

## 3) Tarea para corregir comentario o discrepancia de documentación

**Problema detectado**
- En `ansiterm/README.md` se menciona el paquete `es.juanfranciscoruiz.ansiterm.codes`, pero el código fuente usa `es.nom.juanfranciscoruiz.ansiterm.codes`.
- Esto puede confundir a quien integre la librería o busque clases por paquete.

**Tarea propuesta**
- Corregir las referencias de paquete en el README de `ansiterm` para que coincidan con el código real.
- Revisar rápidamente el resto del README para normalizar términos técnicos (por ejemplo, uso consistente de ANSI/ASCII según corresponda).

**Criterio de aceptación**
- Las rutas de paquete documentadas coinciden con las rutas reales del proyecto.
- La documentación no contiene referencias de paquete obsoletas o incorrectas.

---

## 4) Tarea para mejorar una prueba

**Problema detectado**
- En `TypeConverterTest.testMap2List()` se usa `HashMap` y se compara una lista esperada con orden fijo `[1,2,3]`.
- El orden de iteración de `HashMap` no está garantizado, lo que puede volver la prueba frágil o no determinista.
- En `testExtractDoubleFromString()` ya existe un `TODO` indicando casos faltantes de prueba para excepciones.

**Tarea propuesta**
- Hacer determinista `testMap2List()` usando `LinkedHashMap` o cambiando la aserción a comparación sin orden.
- Completar `testExtractDoubleFromString()` con casos negativos de excepción (cadena sin números, overflow, etc.).

**Criterio de aceptación**
- Las pruebas de `TypeConverterTest` pasan de forma estable en ejecuciones repetidas.
- Se cubren explícitamente casos límite y de error en la extracción de `Double`.
