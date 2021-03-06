PostGis: http://postgis.net/docs/manual-1.4/ch05.html
PostGis Jdbc: https://github.com/postgis/postgis-java

I  Install and use PostGis in PostgreSQL:
1. install
  two ways:
  a. (Only for win)Install postgresql by "EDB binary Installer" and Install postgis bundle by "Aplication Stack Builder".
  b. Install postgresql and postgis separately.
2. create postgis extension and use
CREATE EXTENSION postgis;
CREATE table test_geo(id int, c geometry);
insert into test_geo values(1,st_transform(st_geomfromtext('POINT(10070507.650288 4282901.6281314)',900913),4326));


II PostGis Jdbc Practice(ref doc)
jar：postgresql-jdbc.jar , postgis-jdbc.jar , postgis-jdbc-jtsparser.jar
java:
5.2. Java Clients (JDBC)
Java clients can access PostGIS "geometry" objects in the PostgreSQL database either directly as text representations or using the JDBC extension objects bundled with PostGIS. In order to use the extension objects, the "postgis.jar" file must be in your CLASSPATH along with the "postgresql.jar" JDBC driver package.

import java.sql.*; 
import java.util.*; 
import java.lang.*; 
import org.postgis.*; 

public class JavaGIS { 

public static void main(String[] args) { 

  java.sql.Connection conn; 

  try { 
    /* 
    * Load the JDBC driver and establish a connection. 
    */
    Class.forName("org.postgresql.Driver"); 
    String url = "jdbc:postgresql://localhost:5432/database"; 
    conn = DriverManager.getConnection(url, "postgres", ""); 
    /* 
    * Add the geometry types to the connection. Note that you 
    * must cast the connection to the pgsql-specific connection 
    * implementation before calling the addDataType() method. 
    */
    ((org.postgresql.Connection)conn).addDataType("geometry","org.postgis.PGgeometry")
;
    ((org.postgresql.Connection)conn).addDataType("box3d","org.postgis.PGbox3d");
    /* 
    * Create a statement and execute a select query. 
    */ 
    Statement s = conn.createStatement(); 
    ResultSet r = s.executeQuery("select ST_AsText(geom) as geom,id from geomtable"); 
    while( r.next() ) { 
      /* 
      * Retrieve the geometry as an object then cast it to the geometry type. 
      * Print things out. 
      */ 
      PGgeometry geom = (PGgeometry)r.getObject(1); 
      int id = r.getInt(2); 
      System.out.println("Row " + id + ":");
      System.out.println(geom.toString()); 
    } 
    s.close(); 
    conn.close(); 
  } 
catch( Exception e ) { 
  e.printStackTrace(); 
  } 
} 
}
The "PGgeometry" object is a wrapper object which contains a specific topological geometry object (subclasses of the abstract class "Geometry") depending on the type: Point, LineString, Polygon, MultiPoint, MultiLineString, MultiPolygon.

PGgeometry geom = (PGgeometry)r.getObject(1); 
if( geom.getType() = Geometry.POLYGON ) { 
  Polygon pl = (Polygon)geom.getGeometry(); 
  for( int r = 0; r < pl.numRings(); r++) { 
    LinearRing rng = pl.getRing(r); 
    System.out.println("Ring: " + r); 
    for( int p = 0; p < rng.numPoints(); p++ ) { 
      Point pt = rng.getPoint(p); 
      System.out.println("Point: " + p);
      System.out.println(pt.toString()); 
    } 
  } 
}
The JavaDoc for the extension objects provides a reference for the various data accessor functions in the geometric objects.


