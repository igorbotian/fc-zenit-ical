package me.igorbotian.fczenit.ical.web;

import spark.Spark;

import java.util.Optional;

public class WebApp {

    public static void main(String[] args) {
        Controller cal = new Controller();
        Spark.port(getPort());
        Spark.staticFileLocation("/public");
        Spark.get("/zenit.ics", cal::handleGet);
        Spark.post("/zenit.ics", cal::handleGet);
    }

    private static int getPort() {
        ProcessBuilder pb = new ProcessBuilder();
        return Optional.ofNullable(pb.environment())
                .map(env -> env.get("PORT"))
                .map(Integer::parseInt)
                .orElse(8080);
    }
}
