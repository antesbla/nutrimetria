package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.java.SpringContext;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"com.java", "gui"})
@EntityScan(basePackages = "com.java.model")
@EnableJpaRepositories(basePackages = "com.java.repository")
public class JavaFxApp extends Application {

    private static Scene scene;
    private static ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(JavaFxApp.class).run();
        SpringContext.setApplicationContext(context);
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Inicio"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Nutrimetría");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(JavaFxApp.class.getResource("/Vista/" + fxml + ".fxml"));
        loader.setControllerFactory(context::getBean); // ✅ ya no da error
        return loader.load();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    @Override
    public void stop() {
        context.close();
    }
    
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

}
