package base.engineering_strain_visualiser.engine.rendering;

import java.util.ArrayList;
import java.util.List;

public class ModelLoader
{
	private ArrayList<Float> originalPositions = new ArrayList<Float>();
	private ArrayList<Float> transformedPositions = new ArrayList<Float>();
	private ArrayList<Float> colours = new ArrayList<Float>();

	//Where all the colour-calculating magic happens
	private void calculateColours() {
		
		//Counts up the vertex array in 9 steps (the coordinates for a triangle, i.e 3 3D vertices, 3 * 3 = 9)
		for(int i = 0; i < originalPositions.size(); i+=9) { 		
			List<Float> originalCoords = originalPositions.subList(i, i+9);
			List<Float> transformedCoords = transformedPositions.subList(i, i+9);
	        
			//Distances between a, b, c, on given triangle on the original model
	        float original_AB = (float) Math.sqrt(Math.pow((originalCoords.get(3) - originalCoords.get(0)), 2) + Math.pow((originalCoords.get(4) - originalCoords.get(1)), 2) + Math.pow((originalCoords.get(5) - originalCoords.get(2)), 2));
	        float original_BC = (float) Math.sqrt(Math.pow((originalCoords.get(6) - originalCoords.get(3)), 2) + Math.pow((originalCoords.get(7) - originalCoords.get(4)), 2) + Math.pow((originalCoords.get(8) - originalCoords.get(5)), 2));
	        float original_AC = (float) Math.sqrt(Math.pow((originalCoords.get(6) - originalCoords.get(0)), 2) + Math.pow((originalCoords.get(7) - originalCoords.get(1)), 2) + Math.pow((originalCoords.get(8) - originalCoords.get(2)), 2));
	        
			//Distances between a, b, c, on given triangle on the transformed model
	        float transformed_AB = (float) Math.sqrt(Math.pow((transformedCoords.get(3) - transformedCoords.get(0)), 2) + Math.pow((transformedCoords.get(4) - transformedCoords.get(1)), 2) + Math.pow((transformedCoords.get(5) - transformedCoords.get(2)), 2));
	        float transformed_BC = (float) Math.sqrt(Math.pow((transformedCoords.get(6) - transformedCoords.get(3)), 2) + Math.pow((transformedCoords.get(7) - transformedCoords.get(4)), 2) + Math.pow((transformedCoords.get(8) - transformedCoords.get(5)), 2));
	        float transformed_AC = (float) Math.sqrt(Math.pow((transformedCoords.get(6) - transformedCoords.get(0)), 2) + Math.pow((transformedCoords.get(7) - transformedCoords.get(1)), 2) + Math.pow((transformedCoords.get(8) - transformedCoords.get(2)), 2));
	        
	        //Coordinates of incentre on given triangle on the original model
	        float original_H
	            = (original_AB * originalCoords.get(0) + original_BC * originalCoords.get(3) + original_AC * originalCoords.get(6)) / (original_AB + original_BC + original_AC);
	        float original_K
	            = (original_AB * originalCoords.get(1) + original_BC * originalCoords.get(4) + original_AC * originalCoords.get(7)) / (original_AB + original_BC + original_AC);
	        float original_L
	        	= (original_AB * originalCoords.get(2) + original_BC * originalCoords.get(5) + original_AC * originalCoords.get(8)) / (original_AB + original_BC + original_AC);
			
	        //Coordinates of incentre on given triangle on the transformed model
	        float transformed_H
	        	= (transformed_AB * transformedCoords.get(0) + transformed_BC * transformedCoords.get(3) + transformed_AC * transformedCoords.get(6)) / (transformed_AB + transformed_BC + transformed_AC);
	        float transformed_K
	        	= (transformed_AB * transformedCoords.get(1) + transformed_BC * transformedCoords.get(4) + transformed_AC * transformedCoords.get(7)) / (transformed_AB + transformed_BC + transformed_AC);
	        float transformed_L
        		= (transformed_AB * transformedCoords.get(2) + transformed_BC * transformedCoords.get(5) + transformed_AC * transformedCoords.get(8)) / (transformed_AB + transformed_BC + transformed_AC);
	       	
	        /*V1 - V3 = given triangle on original model
	        V4 - V6 = given triangle on transformed model
	        
	        Using 3D distance formula*/
			float v1_To_Incentre = (float)
					(Math.pow((originalCoords.get(0) - original_H), 2) +
					(Math.pow((originalCoords.get(1) - original_K), 2) +
					(Math.pow((originalCoords.get(2) - original_L), 2)
					)));
			
			float v2_To_Incentre = (float)
					(Math.pow((originalCoords.get(3) - original_H), 2) +
					(Math.pow((originalCoords.get(4) - original_K), 2) +
					(Math.pow((originalCoords.get(5) - original_L), 2)
					)));
			
			float v3_To_Incentre = (float)
					(Math.pow((originalCoords.get(6) - original_H), 2) +
					(Math.pow((originalCoords.get(7) - original_K), 2) +
					(Math.pow((originalCoords.get(8) - original_L), 2)
					)));
			
			float v4_To_Incentre = (float)
					(Math.pow((transformedCoords.get(0) - transformed_H), 2) +
					(Math.pow((transformedCoords.get(1) - transformed_K), 2) +
					(Math.pow((transformedCoords.get(2) - transformed_L), 2)
					)));
			
			float v5_To_Incentre = (float)
					(Math.pow((transformedCoords.get(3) - transformed_H), 2) +
					(Math.pow((transformedCoords.get(4) - transformed_K), 2) +
					(Math.pow((transformedCoords.get(5) - transformed_L), 2)
					)));
			
			float v6_To_Incentre = (float)
					(Math.pow((transformedCoords.get(6) - transformed_H), 2) +
					(Math.pow((transformedCoords.get(7) - transformed_K), 2) +
					(Math.pow((transformedCoords.get(8) - transformed_L), 2)
					)));
			
			//Engineering Strain Formula
			float deltaV1 = ((v4_To_Incentre - v1_To_Incentre) / v1_To_Incentre);
			float deltaV2 = ((v5_To_Incentre - v2_To_Incentre) / v2_To_Incentre);
			float deltaV3 = ((v6_To_Incentre - v3_To_Incentre) / v3_To_Incentre);
			
			//Turns negative strain values into positive numbers to be used as blue colour value
			float negativeV1 = 0;
			if(deltaV1 <= 0) {
				negativeV1 = -1 * deltaV1;
			}
			
			float negativeV2 = 0;
			if(deltaV2 <= 0) {
				negativeV2 = -1 * deltaV2;
			}
			
			float negativeV3 = 0;
			if(deltaV3 <= 0) {
				negativeV3 = -1 * deltaV3;
			}
			
			//RGB values for first vertex
			colours.add(i, deltaV1);
    		colours.add(i + 1, 1 - deltaV1);
    		colours.add(i + 2, negativeV1);
    		
			//RGB values for second vertex
    		colours.add(i + 3, deltaV2);
    		colours.add(i + 4, 1 - deltaV2);
    		colours.add(i + 5, negativeV2);
    		
			//RGB values for third vertex
    		colours.add(i + 6, deltaV3);
    		colours.add(i + 7, 1 - deltaV3);
    		colours.add(i + 8, negativeV3);
    	}
    }
	
    public OriginalModel loadOriginalModel(String modelPath)
    {    	
    	ModelReader.loadOriginalModelFile(modelPath, originalPositions);
		return getOriginalModel(listToArray(originalPositions));
    }
    
    public OriginalModel getOriginalModel(float positions[])
    {
        return new OriginalModel(positions);
    }
    
    public TransformedModel loadTransformedModel(String modelPath)
    {	
    	ModelReader.loadTransformedModelFile(modelPath, transformedPositions, colours);
    	calculateColours();
		return getTransformedModel(listToArray(transformedPositions), listToArray(colours));
    }
    
    public TransformedModel getTransformedModel(float positions[], float colours[])
    {
        return new TransformedModel(positions, colours);
    }
    
	private static float[] listToArray(ArrayList<Float> list)
    {
        float[] array = new float[list.size()];
        
        for(int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}