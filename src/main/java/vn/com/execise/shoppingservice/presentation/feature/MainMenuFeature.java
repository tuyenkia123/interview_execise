package vn.com.execise.shoppingservice.presentation.feature;

import vn.com.execise.shoppingservice.presentation.view.MainMenuView;

public class MainMenuFeature {

    private final MainMenuView mainMenuView;

    public MainMenuFeature(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public void viewMessage(String message) {
        mainMenuView.displayMessage(message);
    }
}
