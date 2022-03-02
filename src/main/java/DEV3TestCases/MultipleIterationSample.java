package DEV3TestCases;
// test comment

public class MultipleIterationSample {
	public void loop()
	{
		for (int index = 0; index < 5; index++) {
			// Another test comment
			/*Multiple line
			 * 
			 */
		}
		
		int x = 3;
		int z = 3 + 7 - 32;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				/* Test result expected is:
				 *   5 comments, 10 lines of comments
				 *   3 loops
				 */
			}
		}
		if(x < z)
		{
			z = x;
		}
	}
	// 42, you know why
}