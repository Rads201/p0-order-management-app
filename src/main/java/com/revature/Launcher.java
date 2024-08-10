package com.revature;

import com.revature.controllers.*;
import io.javalin.Javalin;

public class Launcher {

    public static void main(String[] args) {
        Controller controller = new Controller();
        Javalin app = controller.startAPI();
        app.start(3000);
    }

}
