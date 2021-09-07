package step2;

import step2.ui.menu.MainMenu;

public class StoryAssistant {

    private void startStory(){
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }

    public static void main(String[] args) {
        StoryAssistant storyAssistant = new StoryAssistant();
        storyAssistant.startStory();
    }
}
