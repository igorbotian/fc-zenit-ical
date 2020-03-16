package me.igorbotian.fczenit.ical.web;

import spark.Spark;

import java.util.Optional;

public class FcZenitCalendarWebApp {

    public static void main(String[] args) {
        FcZenitCalendarRoute route = new FcZenitCalendarRoute();
        Spark.port(getPort());
        Spark.get("/zenit.ics", route);
        Spark.post("/zenit.ics", route);
    }

    private static int getPort() {
        ProcessBuilder pb = new ProcessBuilder();
        return Optional.ofNullable(pb.environment())
                .map(env -> env.get("PORT"))
                .map(Integer::parseInt)
                .orElse(8080);
    }
}
