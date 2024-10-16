package week23;

import java.util.ArrayList;
import processing.core.*;

class Shape
{
	float x, y; // position
	float size; // radius
	int colour; // Processing colour representation
	PApplet parent; // parent Processing Applet
	float xt, yt; // target point - used by update
	
	Shape( float x, float y, float size, int colour, PApplet parent )
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.colour = colour;
		this.parent = parent;
		// set a target to move towards
		xt = x;
		yt = y;
	}
	
	void update()
	{
		// is the centre close to the target?
		if ( (x-xt)*(x-xt) + (y-yt)*(y-yt) < 1.0f )
		{
			// choose a new target
			xt = x + parent.random( -50.0f, 50.0f );
			yt = y + parent.random( -50.0f, 50.0f );
		}
		// move towards target
		x = xt * 0.05f + x * 0.95f;
		y = yt * 0.05f + y * 0.95f;
	}
	
	
	void draw()	{} //dummy draw method
	
}

class Circle extends Shape
{
	Circle( float x, float y, float size, int colour, PApplet parent )
	{
		super(x, y, size, colour, parent);
	}
	
	void draw()
	{
		parent.fill( colour );
		parent.ellipse( x, y, size*2.0f, size*2.0f );
	}
	
}

class Star extends Shape
{
	int numPoints;
	
	Star( float x, float y, float size, int colour, int numPoints, PApplet parent )
	{
		super(x, y, size, colour, parent);	//call super constructor: Shape
		this.numPoints = numPoints;
	}
	
	void draw()
	{
		parent.fill( colour );
		float dTheta = (float)Math.PI / (float)numPoints;
		
		parent.beginShape(); // start a filled polygon
		float theta = 0.0f;
		for ( int i = 0; i < numPoints; i++ )
		{
			// add outer vertex 
			parent.vertex( (float)(x + size * Math.cos( theta )), (float)(y + size * Math.sin(theta)) );
			theta += dTheta;
			// add inner vertex
			parent.vertex( (float)(x + 0.5f*size * Math.cos( theta )), (float)(y + 0.5f*size * Math.sin(theta)) );
			theta += dTheta;
		}
		parent.endShape(parent.CLOSE); // end filled polygon
	}
	
}

public class RandomShapes extends PApplet
{
	ArrayList<Shape> shapeList;	//arraylist of different shapes
	
	final int numShapes = 100;
	
	int randomColour()
	{
		return color( (int)random(255), (int)random(255), (int)random(255) );
	}
 	public void setup()
	{
		size( 500, 500 );
		shapeList = new ArrayList<Shape>();
	
		// create some random circles and stars
		int i;
		for ( i = 0; i < numShapes; i++ )
		{
			float x = random(width);
			float y = random(height);
			float size = random( height*0.1f );
			int colour = randomColour();
			
			if (i%2 == 0)		// if i is even, make circle
				shapeList.add( new Circle(x, y, size, colour, this ));
			else		//i is odd, make star
			{
				int numPoints = (int)random( 4, 10 );
			shapeList.add( new Star(x, y, size, colour, numPoints, this ));
			}
		}
		
	}
	
 	public void draw()		// show polymorphic behaviour
	{
 		// clear to white
 		background(255);
 		// draw the circles
		for ( Shape sh: shapeList )
		{
			sh.update();
			sh.draw();
		}
	}
 	
}