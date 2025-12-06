/* Paquete de la clase */
package ux.mx.listi.Backend_Foro.middleware;

public class RoleMiddleware {
    // 
    public static String denegarModificacionRol(String idUsuarioLogueado, String idUsuarioAModificar){
        
        // Si el usuario intenta modificarse a sí mismo, se manda bn lejos
        if (idUsuarioLogueado.equals(idUsuarioAModificar)) {
            return "No puedes modificar tu propio rol";
        }
        
        return null;
    }
}
