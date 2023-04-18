package server;

/**
 * Cette interface fonctionnelle définit une méthode pour gérer les événements en fonction de la commande et
 * des arguments fournis. Elle appelle la méthode handleRegistration() lorsque la commande est égale à la
 * constante REGISTER_COMMAND et la méthode handleLoadCourses(arg) lorsque la commande est égale à la constante
 * LOAD_COMMAND.
 */
@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
