package es.nom.juanfranciscoruiz.ansiterm.util;

import es.nom.juanfranciscoruiz.utiles.TypeConverter;
import es.nom.juanfranciscoruiz.utiles.Types;
import es.nom.juanfranciscoruiz.utiles.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

/**
 * Controla un menú de opciones, lo muestra en pantalla y recoge la respuesta 
 * del usuario.
 * 
 * @author juanf
 */
public class Menu {
    
    /**
     * Usado para trazas
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Menu.class);
    
    /**
     * La lista de opciones del menú.
     */
    private List<String> opciones;
    
    /**
     * El título del menú
     */
    private String titulo;
    /**
     * Un mensaje que aparecerá debajo de la lista de opciones del menú y que 
     * suele utilzarse para indicar mensajes al usuario
     */
    private String mensaje;
    
    /**
     * Contiene la opción seleccionada por el usuario
     */
    private Long opcionSeleccionada;
    
    /**
     * En el caso de que una aplicación tenga varios menús si esMenuInicio es 
     * verdadero, entonces este es el menú principal de la aplicación y se le 
     * agregará como primera opción, la opción "0. Salir de la aplicación".
     */
    private boolean esMenuInicio;

    /**
     * Instancia un objeto Menu 
     */
    public Menu(){
        this.opciones = new ArrayList<>();
        this.esMenuInicio = false;
        this.titulo = "Sin título";
        this.mensaje = "";
    }
    
    /**
     * Instancia un objeto Menu con los parámetros indicados
     * @param opciones Una lista de opciones
     * @param titulo Una cadena con el título del menú
     * @param mensaje Una cadena con el mensaje que mostrará debajo de la lista 
     * de opciones
     * @param esMenuInicio booleano que indica si es el menú principal de la 
     * aplicación, lo que agregará la opción "0. Salir de la aplicación" a la 
     * propiedad opciones.
     */
    public Menu(List<String> opciones, String titulo, String mensaje, boolean esMenuInicio) {
        if (opciones != null && !opciones.isEmpty()) {
            this.opciones = opciones;
        } else {
            this.opciones = new ArrayList<>();
        }
        if (titulo != null && !titulo.isEmpty()) {
            this.titulo = titulo;
        } else {
            this.titulo = "Sin titulo";
        }
        if (mensaje != null && !mensaje.isEmpty()){
            this.mensaje = mensaje;
        } else {
            this.mensaje = mensaje;
        }
        
        this.esMenuInicio = esMenuInicio;
        if (this.esMenuInicio) {
            this.opciones.add(0,"0. Salir de la aplicación");
        }
        
    }

    /**
     * Obtiene la lista de opciones del menú
     * @return Una lista de strings con las opciones.
     */
    public List<String> getOpciones() {
        return opciones;
    }

    /**
     * Establece la lista de opciones del menú
     * @param opciones Una lista de strings con las nuevas opciones del menú.
     */
    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
        if (this.esMenuInicio) {
            this.opciones.add(0,"0. Salir de la aplicación");
        }
    }

    /**
     * Obtiene el título del menú
     * @return una cadena con el título del menú
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del menú
     * @param titulo Una cadena con el nuevo título del menú
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el mensaje del menú
     * @return una cadena con el mensaje actual del menú
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece un nuevo mensaje en el menú
     * @param mensaje Una cadena con el nuevo mensaje del menú
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene la opción seleccionada actualmente por el usuario (después de llamar
     * al método esperarRespuesta() ).
     * @return un long con la opción seleccionada por el usuario
     */
    public Long getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    /**
     * Establece la opción seleccionada
     * @param opcionSeleccionada Un long con la opción seleccionada
     */
    public void setOpcionSeleccionada(Long opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }

    /**
     * Devuelve un booleano que indica si este menú es el menú principal de la 
     * aplicación.
     * 
     * @return un booleano, si es true indica que el menú actual es el menú 
     * principal de la aplicación y false, en caso contrario.
     * 
     */
    public boolean isEsMenuInicio() {
        return esMenuInicio;
    }

    /**
     * Establece el booleano que indica si el menú actual es el menú principal.
     * Si se pasa 'true' se agregará como primera opción a la lista de opciones 
     * la opción "0. Salir de la aplicación"
     * @param esMenuInicio booleano, si es true indica que el menú actual es el menú 
     * principal de la aplicación y false, en caso contrario.
     */
    public void setEsMenuInicio(boolean esMenuInicio) {
        //TODO: Si se establece en false, habrá que comprobar si tiene la opción
        // "0. Salir de la aplicación" y eliminarla.
        this.esMenuInicio = esMenuInicio;
         if (this.esMenuInicio) {
            this.opciones.add(0,"0. Salir de la aplicación");
        }
    }

    /**
     * Muestra el menú en la salida estándar.
     */
    public void mostrar(){
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        int longitud = this.getTitulo().length();
        for (int i = 0; i < longitud + 5; i++) {
            sb.append("*");
        }
        sb.append(System.lineSeparator());
        sb.append("  ").append(this.getTitulo()).append("  ");
        sb.append(System.lineSeparator());
        for (int i = 0; i < longitud + 5; i++) {
            sb.append("*");
        }
        
        String tit = sb.toString();
        System.out.println(tit);
        System.out.println("");
        System.out.println("");
        for (String opcion : this.getOpciones()){
            System.out.println(opcion);
        }
        System.out.println("");
        System.out.println(this.getMensaje());
        System.out.println("");
    }
    
    /**
     * Espera la respuesta del usuario, que devuelve como un long, o -1 en caso 
     * de producirse algún error.
     * Este método controla los errores, devolviendo -1 en caso de:<br>
     * <ul>
     * <li>Lo introducido no sea un número.</li>
     * <li>Lo introducido sea un número, pero fuera del rango de la lista de opciones</li>
     * <li>Se pulse directamente INTRO.</li>
     * </ul>
     * También establece el campo 'mensaje' con el error producido, para que 
     * luego el método mostrar() lo saque en pantalla.
     * 
     * @param msg Una cadena opcional con el texto que se imprimirá a la izquierda
     * del prompt del usuario. Si no se indica nada, se imprimirá la frase 
     * "Haga su selección: ".
     * @return Un long con la opción elegida o -1 en caso de producirse algún 
     * error.
     */
    public Long esperarRespuesta(String msg){
        if (msg == null || msg.isEmpty()) {
            msg = "Haga su selección: ";
        }
        String respuesta;
        
        int opcMaxima = this.getOpciones().size() - 1;
        Long resp;
        System.out.println(msg);
        try {
            
            try (Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in))) {
                    respuesta = sc.nextLine();
                }
            if (!Types.isNullOrEmpty(respuesta)) {
                if (Types.isInteger(respuesta)){
                    resp = TypeConverter.extractLongFromString(respuesta);
                    if (resp == -1L){
                        this.setMensaje("No ha indicado un número válido");
                        resp = -1L;
                    } else if (resp < 0 || resp > opcMaxima) {
                        this.setMensaje("La opción seleccionada está fuera del rango permitido");
                        resp = -1L;
                    }
                } else {
                    this.setMensaje("Lo introducido no es un número");
                    resp = -1L;
                } 
            } else {
                this.setMensaje("Debe introducir un número con la opción a elegir.");
                resp = -1L;
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            resp = -1L;
        }
        this.setOpcionSeleccionada(resp);
        return this.getOpcionSeleccionada();
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu{");
        sb.append("opciones=").append(Util.CollectionToString(opciones, true, 10));
        sb.append(", titulo=").append(titulo);
        sb.append(", mensaje=").append(mensaje);
        sb.append(", opcionSeleccionada=").append(opcionSeleccionada);
        sb.append(", esMenuInicio=").append(esMenuInicio);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
