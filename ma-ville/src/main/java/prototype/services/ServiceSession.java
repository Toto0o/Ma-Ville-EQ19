package prototype.services;

import prototype.controllers.ApiController;

/**
 * Singleton pour obtenir {@link ApiController}
 *
 * <p>Assure une seule instance du controlleur et le rend acessible à toutes les scenes</p>
 */
public class ServiceSession {

    private static ServiceSession instance;
    private ApiController controller;

    private ServiceSession() {}

    public static ServiceSession getInstance() {
        if (instance == null) {
            instance = new ServiceSession();
        }
        return instance;
    }

    public ApiController getController() {
        if (controller == null) {
            controller = new ApiController();
        }
        return controller;
    }
    public void setController(ApiController controller) {
        this.controller = controller;
    }
}
