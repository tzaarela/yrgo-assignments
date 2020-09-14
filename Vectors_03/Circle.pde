public class Circle
{
	Vector2 position;
	Vector2 direction;

	float speed;
	float width;
	float height;
	float friction = 2;

	color circleColor;

	boolean isMoving;

	public Circle(Vector2 position, float width, float height, color circleColor)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		this.circleColor = circleColor;
	}

	public void Move(Vector2 direction)
	{
		if(speed > 0)
		{
			this.position.x -= direction.x * speed;
			this.position.y -= direction.y * speed;
			speed = speed - friction;
		}
		else
		{
			isMoving = false;
		}
	}

	public void Draw()
	{
		noStroke();
		fill(circleColor);
		ellipse(position.x, position.y, width, height);
	}

}