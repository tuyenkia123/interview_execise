package vn.com.execise;


import vn.com.execise.config.ApplicationFactory;
import vn.com.execise.shoppingservice.presentation.ApplicationCLI;

public class Application {

    public static void main(String[] args) {
        var factory = new ApplicationFactory();

        var application = new ApplicationCLI(factory.getProductFeature(),
                factory.getCartFeature(),
                factory.getMainMenuFeature());
        application.start();
    }
}
