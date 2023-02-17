package base.engineering_strain_visualiser.engine.rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import java.nio.*;
import java.util.*;

import static org.lwjgl.opengl.GL30.*;

public class TransformedModel {

    private int vertexCount;
    private int vaoID;
    private List<Integer> vboIDList;
    
    //Takes in Colour
    public TransformedModel(float positions[], float colours[]) {
    	
    	vertexCount = positions.length / 3;
    	vboIDList = new ArrayList<>();
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        
        // Positions VBO
        int vboID = glGenBuffers();
        vboIDList.add(vboID);
        FloatBuffer positionsBuffer = storeDataInFloatBuffer(positions);
        positionsBuffer.put(0, positions);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Color VBO
        vboID = glGenBuffers();
        vboIDList.add(vboID);
        FloatBuffer coloursBuffer = storeDataInFloatBuffer(colours);
        coloursBuffer.put(0, colours);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, coloursBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    
    public FloatBuffer storeDataInFloatBuffer(float data[]) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
    
    public void cleanUp() {
        vboIDList.stream().forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoID);
    }

	public int getVertexCount() {
		return vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}
}