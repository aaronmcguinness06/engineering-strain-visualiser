package base.engineering_strain_visualiser.engine.shaders;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/res/shaders/vertex.glsl";
	private static final String FRAGMENT_FILE = "src/res/shaders/fragment.glsl";

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}