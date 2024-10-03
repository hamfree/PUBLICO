package es.nom.juanfranciscoruiz.ansiterm.util;

import es.nom.juanfranciscoruiz.utiles.TypeConverter;
import es.nom.juanfranciscoruiz.utiles.IO;
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

    public Menu(){
        this.opciones = new ArrayList<>();
        this.esMenuInicio = false;
        this.titulo = "Sin título";
        this.mensaje = "";
    }
    
    public Menu(List<String> opciones, String titulo, String mensaje, boolean esMenuInicio) {
        if (opciones != null && !opciones.isEmpty()) {
            this.opciones = opciones;
        } else {
            this.opciones = new ArrayList<>();
        }
        if (titulo != null && !titulo.isBlank()) {
            this.titulo = titulo;
        } else {
            this.titulo = "Sin titulo";
        }
        if (mensaje != null && !mensaje.isBlank()){
            this.mensaje = mensaje;
        } else {
            this.mensaje = mensaje;
        }
        
        this.esMenuInicio = esMenuInicio;
        if (this.esMenuInicio) {
            this.opciones.addFirst("0. Salir de la aplicación");
        }
        
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
        if (this.esMenuInicio) {
            this.opciones.addFirst("0. Salir de la aplicación");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public void setOpcionSeleccionada(Long opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }

    public boolean isEsMenuInicio() {
        return esMenuInicio;
    }

    public void setEsMenuInicio(boolean esMenuInicio) {
        this.esMenuInicio = esMenuInicio;
         if (this.esMenuInicio) {
            this.opciones.addFirst("0. Salir de la aplicación");
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
     * Este método controla los errores, devolviendo -1 en caso de:
     * - Lo introducido no sea un número.
     * - Lo introducido sea un número, pero fuera del rango de la lista de opciones
     * - Se pulse directamente INTRO.
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
        if (msg == null || msg.isBlank()) {
            msg = "Haga su selección: ";
        }
        String respuesta;
        
        int opcMaxima = this.getOpciones().size() - 1;
        Types t = new Types();
        Long resp = 0L;
        System.out.println(msg);
        try {
            
            try (Scanner sc = new Scanner(new UnclosableInputStreamDecorator(System.in))) {
                    respuesta = sc.nextLine();
                }
            if (!Types.isNullOrEmpty(respuesta)) {
                if (t.isInteger(respuesta)){
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
        sb.append("opciones=").append(Util.toString(opciones, true, 10));
        sb.append(", titulo=").append(titulo);
        sb.append(", mensaje=").append(mensaje);
        sb.append(", opcionSeleccionada=").append(opcionSeleccionada);
        sb.append(", esMenuInicio=").append(esMenuInicio);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
