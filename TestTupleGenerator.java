 
/*****************************************************************************************
 * @file  TestTupleGenerator.java
 *
 * @author   Sadiq Charaniya, John Miller
 */

import static java.lang.System.out;

/*****************************************************************************************
 * This class tests the TupleGenerator on the Student Registration Database defined in the
 * Kifer, Bernstein and Lewis 2006 database textbook (see figure 3.6).  The primary keys
 * (see figure 3.6) and foreign keys (see example 3.2.2) are as given in the textbook.
 */
public class TestTupleGenerator
{
    /*************************************************************************************
     * The main method is the driver for TestGenerator.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        var test = new TupleGeneratorImpl ();
        var tables = new String [] { "Student", "Professor", "Course", "Teaching", "Transcript" };


        test.addRelSchema (tables[0],
                           "id name address status",
                           "Integer String String String",
                           "id",
                           null);
        
        test.addRelSchema (tables[1],
                           "id name deptId",
                           "Integer String String",
                           "id",
                           null);
        
        test.addRelSchema (tables[2],
                           "crsCode deptId crsName descr",
                           "String String String String",
                           "crsCode",
                           null);
        
        test.addRelSchema (tables[3],
                           "crsCode semester profId",
                           "String String Integer",
                           "crcCode semester",
                           new String [][] {{ "profId", "Professor", "id" },
                                            { "crsCode", "Course", "crsCode" }});
        
        test.addRelSchema (tables[4],
                           "studId crsCode semester grade",
                           "Integer String String String",
                           "studId crsCode semester",
                           new String [][] {{ "studId", "Student", "id"},
                                            { "crsCode", "Course", "crsCode" },
                                            { "crsCode semester", "Teaching", "crsCode semester" }});

        //var tups   = new int [] { 10000, 1000, 2000, 50000, 5000 };
        //var tups   = new int [] { 5, 5, 5, 5, 5 };
        
        
        //Test:1
        var tups   = new int [] { 100000, 10, 20, 50, 50000 };
        
        var resultTest = test.generate (tups);
        
        for (var i = 0; i < resultTest.length; i++) {
            out.println (tables [i]);
            for (var j = 0; j < resultTest [i].length; j++) {
            	test.tableLink.get(tables[i]).insert(resultTest[i][j]); 
            	
                //for (var k = 0; k < resultTest [i][j].length; k++) {
                    //out.print (resultTest [i][j][k] + ",");
                //} // for
                //out.println ();
            } // for
            
            //test.tableLink.get(tables[i]).print(); 
            //out.println ();
        } // for
        
        //--------------------- equi-join: movie JOIN studio ON studioName = name

        
        out.println ();
        long startTime = System.nanoTime();
        var t_join = test.tableLink.get(tables[4]).i_join("studId", "id", test.tableLink.get(tables[0]));
        long elapsedNanos = System.nanoTime() - startTime;
        t_join.print ();
        System.out.print("TIME TAKEN: " + elapsedNanos);

        
    } // main

} // TestTupleGenerator

