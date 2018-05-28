import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App{
    public static void main(String[] args){
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        // ProcessBuilder process = new ProcessBuilder();
        // Integer port;
        // if (process.environment().get("PORT") != null) {
        //  port = Integer.parseInt(process.environment().get("PORT"));
        //     } else {
        //         port = 4567;
        // }
        // setPort(port);
        
        get("/", (request, respond) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/home", (request, respond) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("sightings", Sighting.all());
            model.put("animals",Animal.all());
            model.put("AnimalClass", Animal.class);
            model.put("template", "templates/home.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/details", (request, respond) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            String ranger = request.queryParams("ranger");
            String animal = request.queryParams("animal");
            String endangered = request.queryParams("endangered");
            String location = request.queryParams("location");
            String health = request.queryParams("health");
            String age = request.queryParams("age");
            Animal newAnimal = new Animal(animal, endangered, health, age);
            newAnimal.save();
            Sighting newSighting = new Sighting(ranger, location, newAnimal.getId());
            newSighting.save();
            model.put("sightings", Sighting.all());
            model.put("animals",Animal.all());
            model.put("AnimalClass", Animal.class);
            model.put("template", "templates/home.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
    }
}