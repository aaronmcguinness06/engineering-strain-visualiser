package base.engineering_strain_visualiser.main;

import base.engineering_strain_visualiser.engine.core.Engine;
import base.engineering_strain_visualiser.engine.core.App;
import base.engineering_strain_visualiser.engine.core.IAppLogic;

public class Main {	
	public static void main(String[] args) {
		IAppLogic appLogic = new App();
		Engine engine = new Engine
				("Engineering Strain Visualiser", 1000, 600, "original", "transformed", appLogic);
		engine.start();
	}
}