package pe.edu.upeu.cafeSnoopy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;

@SpringBootApplication
public class AsistenciaApplication extends Application {

	private ConfigurableApplicationContext context;
	private Parent parent;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(AsistenciaApplication.class);
		builder.application().setWebApplicationType(WebApplicationType.NONE);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));

		// CORREGIR: Usar getResource con la ruta correcta
		URL fxmlUrl = getClass().getResource("/fxml/maingui.fxml");
		if (fxmlUrl == null) {
			throw new IllegalStateException("No se pudo encontrar maingui.fxml en /fxml/");
		}

		FXMLLoader loader = new FXMLLoader(fxmlUrl);
		loader.setControllerFactory(context::getBean);
		parent = loader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(parent, 1000, 700));
		stage.setTitle("Cafe Snoopy - Sistema de Gestión");
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		if (context != null) {
			context.close();
		}
	}
}