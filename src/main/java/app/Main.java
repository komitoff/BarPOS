package app;

import app.cores.StageManager;
import app.enums.ViewPath;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {
	private final static String currentDir = System.getProperty("user.dir");

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;


	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}

	@Override
	public void init() throws Exception {
		springContext = bootstrapSpringApplicationContext();
//		this.springContext = SpringApplication.run(Main.class);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, stage);
		stageManager.switchScene(ViewPath.MANAGER);

	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}

	private ConfigurableApplicationContext bootstrapSpringApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
