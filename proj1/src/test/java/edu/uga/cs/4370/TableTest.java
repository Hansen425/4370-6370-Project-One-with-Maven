/****************************************************************************************
 *@file TableTest.java
 *
 *@author Nick Klepp, Trent Walls, Jason Vinson, Theresa Miller
 */

import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.*;

public class TableTest extends TestCase{

    private Table movie;
    private Table movie2;
    private Table movie3;
    private Table movie4;
    private Table movie5;
    
    protected void setUp(){
	movie = new Table ("movie", "title year length genre studioName producerNo",
				 "String Integer Integer String String Integer", "title year");

	movie2 = new Table ("movie2", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");

	movie3 = new Table ("movie3", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");

	movie4 = new Table ("movie4", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");
	movie5 = new Table("movie5", "title year","String Integer","title year");
	
	Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
	Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
	Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
	Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };
       	Comparable [] film4 = {"Star_Wars",1977};
	Comparable [] film5 = { "Star_Wars_2", 1980};
	Comparable [] film6 = { "Rocky", 1985};
	Comparable [] film7 = { "Rambo", 1978};

	movie.insert(film0);
	movie.insert(film1);
	movie.insert(film2);
	movie.insert(film3);
	//movie.print();

	movie2.insert(film0);
	movie2.insert(film1);   
	//movie2.print();

	movie3.insert(film2);
	movie3.insert(film3);
	//movie3.print();

	movie4.insert(film0);

	movie5.insert(film4);
	movie5.insert(film5);
	movie5.insert(film6);
	movie5.insert(film7);
    }
    
    

    @Test
    public void testMinus(){

	/*
	Table movie = new Table ("movie", "title year length genre studioName producerNo",
				 "String Integer Integer String String Integer", "title year");

	Table movie2 = new Table ("movie2", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");

	Table movie3 = new Table ("movie3", "title year length genre studioName producerNo",
				  "String Integer Integer String String Integer", "title year");
    
	Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
	Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
	Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
	Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };

	movie.insert(film0);
	movie.insert(film1);
	movie.insert(film2);
	movie.insert(film3);
	movie.print();

	movie2.insert(film0);
	movie2.insert(film1);   
	movie2.print();

	movie3.insert(film2);
	movie3.insert(film3);
	movie3.print();*/

	Table newMovie=movie.minus(movie2);
	//newMovie.print();

	//System.out.println("movie3 tuples: " + movie3.getTuples().toString());
	//System.out.println("newMovie tuples: " + newMovie.getTuples().toString());
	assertTrue(movie3.equals(newMovie));
    }

    @Test
    public void testIndexSelect(){
	KeyType key1=new KeyType(new Comparable[]{"Star_Wars",1977});
	Table newMovie=movie.select(key1);
	//System.out.println("newMovie: ");
	//newMovie.print();
	assertTrue(newMovie.equals(movie4));
    } 

    @Test
    public void testUnion(){
	assertTrue(movie.equals(movie2.union(movie3)));
    }

    @Test
    public void testProject(){
	System.out.println("movie5: ");
	movie5.print();
	Table project_test=movie.project("title year");
	System.out.println("project_test:" );
	project_test.print();
	assertTrue(movie5.equals(movie.project("title year")));
    }

    
    @Test
    public void testEquiJoin(){
	//ARRANGE
	Table movie_cast = new Table ("movie_cast", "title year length genre studioName producerNo",
				 "String Integer Integer String String Integer", "title year");

	Table movie2_cast = new Table ("movie2_cast", "title year actor1 actor2 actor3 actor4",
				  "String Integer String String String String", "title year");



	Comparable [] film0 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
	Comparable [] film1 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
	Comparable [] film2 = { "Rocky", 1985, 200, "action", "Universal", 12125 };
	Comparable [] film3 = { "Rambo", 1978, 100, "action", "Universal", 32355 };

	movie_cast.insert(film0);
	movie_cast.insert(film1);
	movie_cast.insert(film2);
	movie_cast.insert(film3);
	movie_cast.print();

	Comparable [] joinFilm0 = {"Star_Wars", 1977, "Han", "Luke", "Yoda", "Bob"};
	Comparable [] joinFilm1 = {"Star_Wars_2", 1980, "Padme", "Jango", "Zam", "Joe"};
	movie2_cast.insert(joinFilm1);
	movie2_cast.insert(joinFilm0);

	movie2_cast.print();

	//ACT
	Table joinTable = movie_cast.join("title year", "title year", movie2_cast);//EquiJoin
	Table equiJoin_correct = new Table (
       				    "movie11", "title year length genre studioName producerNo title2 year2 actor1 actor2 actor3 actor4",
       				    "String Integer Integer String String Integer String Integer String String String String", "title year");

	Comparable [] eqJ_correct1 = { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345, "Star_Wars", 1977, "Han", "Luke", "Yoda", "Bob"};
	Comparable [] eqJ_correct2 = { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345, "Star_Wars_2", 1980, "Padme", "Jango", "Zam", "Joe"};
	equiJoin_correct.insert(eqJ_correct1);
	equiJoin_correct.insert(eqJ_correct2);
	System.out.println("Equijoin: ");
	equiJoin_correct.print();
	System.out.println("result of movie.join(\"title year\", \"title year\",movie2 :" );
	joinTable.print();
	assertTrue(equiJoin_correct.getTuples().size() == joinTable.getTuples().size());
	//    equiJoin_correct.printIndex();
	//    joinTable.printIndex();
	//    List<Comparable []> tuples1 = equiJoin_correct.getTuples();
	//    List<Comparable []> tuples2 = joinTable.getTuples();


	Table naturalJoin = movie_cast.join(movie2_cast);
	System.out.println("Natural Join Table: ");
	naturalJoin.print();

	//ASSERT
	assertTrue(equiJoin_correct.getTuples().size() == joinTable.getTuples().size());
	assertTrue(equiJoin_correct.equals(joinTable));
	//    Table selectTest = joinTable.select(new KeyType(new Comparable[]{"Star_Wars",1977}));
	//    selectTest.print();

	//    assertTrue(joinTable.equals(equiJoin_correct));

	//    for( int i = 0; i < tuples1.size(); i++){
	//    System.out.println(i);
	//    assertTrue(tuples1.get(i).equals(tuples2.get(i)));
	//    }
	//    assertTrue(tuples1 == tuples2);
	//    assertTrue(joinTable.compatible(equiJoin_correct));

    }
    
}
