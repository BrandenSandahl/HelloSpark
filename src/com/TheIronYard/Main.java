package com.TheIronYard;

import spark.ModelAndView;
import spark.*;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static User user = null;

    public static void main(String[] args) {
        Spark.init();


        Spark.get(
                "/",
                ((request, response) -> {  //anon function
                    HashMap m = new HashMap();
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
                    user = new User(name);
                    response.redirect("/");
                    return "";
                }));

    }
}
