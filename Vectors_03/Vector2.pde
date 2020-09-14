public class Vector2
{
	float x;
	float y;

	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	float Magnitude()
	{
		return sqrt(x * x + y * y);
	}

	Vector2 Normalize()
	{
		float mag = Magnitude();
		if (mag > 0)
		{
			return new Vector2(x / mag, y / mag);
		}

		return new Vector2(0,0);
	}

	float Distance(Vector2 target)
	{
		float targetDistance =  new Vector2(x - target.x, y - target.y).Magnitude();
		return targetDistance;
	}
}