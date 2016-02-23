package com.TheIronYard;

import spark.ModelAndView;
import spark.*;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Spark.init();


        Spark.get(
                "/",
                ((request, response) -> {  //anon function
                    HashMap m = new HashMap();

                    Session session = request.session(); //find a session
                    String userName = session.attribute("userName"); //pulls the value out of the session by the key
                    User user = users.get(userName);


                    if (user != null) {
                        m.put("name", user.name);
                        return new ModelAndView(m, "home.html");
                    } else {
                        return new ModelAndView(m, "login.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) ->  {
                    String name = request.queryParams("loginName");
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name);
                        users.put(name, user);
                    }

                    Session session = request.session();  //this creates a "cookie" style object to track indiv users
                    session.attribute("userName", name);  //attach the current user to session


                    response.redirect("/");
                    return "";
                }));
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

    }
}
