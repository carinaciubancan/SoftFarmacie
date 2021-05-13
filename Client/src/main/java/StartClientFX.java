import controllers.FarmacistController;
import controllers.LoginController;
import controllers.MedicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServices;

public class StartClientFX extends Application {
    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");

        try{
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IServices server=(IServices)factory.getBean("service");
            System.out.println("Obtained a reference to remote  server");

            //login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
            Parent root=loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setContext(server);


            FXMLLoader bLoader = new FXMLLoader(getClass().getResource("/SpitalView.fxml"));
            Parent bRoot = bLoader.load();
            MedicController medicController = bLoader.getController();
            medicController.setContext(server);

            FXMLLoader aLoader = new FXMLLoader(getClass().getResource("/FarmacieView.fxml"));
            Parent aRoot = aLoader.load();
            FarmacistController farmacistController = aLoader.getController();
            farmacistController.setContext(server);

            ctrl.setControllerMedic(medicController);
            ctrl.setControllerFarmacist(farmacistController);

            ctrl.setParents(bRoot,aRoot);

            primaryStage.setTitle("Spital");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        }catch (Exception e) {
            System.err.println("Spital Initialization exception:"+e);
            e.printStackTrace();
        }

    }
}
