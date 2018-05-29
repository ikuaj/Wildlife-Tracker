import org.sql2o.*;

public class DB{
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5400/wildlife_tracker", "postgres", "braeburn");
}