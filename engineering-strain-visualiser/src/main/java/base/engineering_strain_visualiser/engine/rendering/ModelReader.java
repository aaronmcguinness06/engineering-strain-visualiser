package base.engineering_strain_visualiser.engine.rendering;

import java.util.ArrayList;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

public class ModelReader {
	
	public static void loadOriginalModelFile(String filepath, ArrayList<Float> positions) {
		AIScene scene = Assimp.aiImportFile(filepath, Assimp.aiProcess_Triangulate);
		
		PointerBuffer buffer = scene.mMeshes();
		
		for(int i = 0; i < buffer.limit(); i++) {
			AIMesh mesh = AIMesh.create(buffer.get(i));
			processOriginalMesh(mesh, positions);
		}
	}
	
	private static void processOriginalMesh(AIMesh mesh, ArrayList<Float> positions) {
		AIVector3D.Buffer vectors = mesh.mVertices();
		
		for(int i = 0; i < vectors.limit(); i++) {
			AIVector3D vector = vectors.get(i);
			
			positions.add(vector.x());
			positions.add(vector.y());
			positions.add(vector.z());
		}
	}
	
	//Transformed Model Takes in Colours
	public static void loadTransformedModelFile(String filepath, ArrayList<Float> positions, ArrayList<Float> colours) {
		AIScene scene = Assimp.aiImportFile(filepath, Assimp.aiProcess_Triangulate);
		
		PointerBuffer buffer = scene.mMeshes();
		
		for(int i = 0; i < buffer.limit(); i++) {
			AIMesh mesh = AIMesh.create(buffer.get(i));
			processTransformedMesh(mesh, positions, colours);
		}
	}
	
	private static void processTransformedMesh(AIMesh mesh, ArrayList<Float> positions, ArrayList<Float> colours) {
		AIVector3D.Buffer vectors = mesh.mVertices();
		
		for(int i = 0; i < vectors.limit(); i++) {
			AIVector3D vector = vectors.get(i);
			
			positions.add(vector.x());
			positions.add(vector.y());
			positions.add(vector.z());
		}
	}
}