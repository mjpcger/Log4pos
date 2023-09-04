import net.bplaced.conrad.log4jpos.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Level[] levels = {Level.OFF, Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE, Level.ALL};
        String[] layouts = {
                "%d %m %n %p %-10t %10Y %3n",
                "%d{mmHH SSS} %-3n %5m %-6Y%-2n"
        };
        String[] patterns = {
                ".m", ".ymd", ".yyyywu", ".yyyyMMdd"
        };
        String path = "MicroLog4JTest";
        Logger log = Logger.getLogger("Test");
        for (Level lev : levels) {
            log.setLevel(lev);
            System.out.println("Set level: " + lev);
            try {
                Appender app = new FileAppender(new SimpleLayout(), path);
                log.addAppender(app);
                String text= "SimpleLayout: " + lev.toString();
                for (Level lv : levels)
                    log.log(lv, text);
                log.removeAllAppenders();
                for (String pat : patterns) {
                    for (String lay : layouts) {
                        app = new DailyRollingFileAppender(new PatternLayout(lay), path, pat);
                        log.addAppender(app);
                        log.log(Level.INFO, lay + " - " + pat);
                        log.removeAllAppenders();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}