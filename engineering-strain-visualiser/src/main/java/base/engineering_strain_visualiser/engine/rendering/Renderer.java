package base.engineering_strain_visualiser.engine.rendering;

import static org.lwjgl.opengl.GL30.*;

public class Renderer {
	
	public void render(TransformedModel mesh) {
		glBindVertexArray(mesh.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
		
		//Back-Face Culling
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
}