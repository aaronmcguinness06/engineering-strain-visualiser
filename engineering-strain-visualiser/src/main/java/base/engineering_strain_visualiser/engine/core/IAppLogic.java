package base.engineering_strain_visualiser.engine.core;

import base.engineering_strain_visualiser.engine.window.Window;

public interface IAppLogic {
	
	void init(Window window);

    void update(Window window);
}