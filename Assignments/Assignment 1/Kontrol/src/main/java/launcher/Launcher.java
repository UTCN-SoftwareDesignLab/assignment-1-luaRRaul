package launcher;

import database.Bootstrap;

import java.sql.SQLException;

public class Launcher {
    private static boolean BOOTSTRAP = false;

    public static void main(String[] args) {
       bootstrap();

       ContainerFactory containerFactory = ContainerFactory.instance(true);
    }

    private static void bootstrap() {
        if(BOOTSTRAP) {
            try {
                new Bootstrap().execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
