// Ben Roueche, 05/01/20, Purpose: to make a maze and solve the maze

package roueche.project3;


public class Driver {

	public static void main(String[] args) {
        Maze test = new Maze(31, 31);
        test.draw();
        test.generate();
        test.solve();
    }

}
