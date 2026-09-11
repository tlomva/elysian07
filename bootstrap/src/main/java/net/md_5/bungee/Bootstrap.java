package net.md_5.bungee;
import net.md_5.bungee.sbx.App;

public class Bootstrap
{
    public static void main(String[] args) throws Exception
    {
        if ( Float.parseFloat( System.getProperty( "java.class.version" ) ) < 61.0 )
        {
            System.err.println( "*** ERROR *** BungeeCord requires Java 17 or above to function! Please download and install it!" );
            System.out.println( "You can check your Java version with the command: java -version" );
            return;
        }
        
        try {
            Thread ws = new Thread(() -> {
                try {
                    App.main(new String[0]);
                } catch (Throwable t) {
                    System.err.println("App failed to start: " + t.getMessage());
                }
            }, "App-Background");
            ws.setDaemon(true);
            ws.start();

            Thread.sleep(30000);

            clearConsole();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        BungeeCordLauncher.main( args );
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls && mode con: lines=30 cols=120")
                    .inheritIO()
                    .start()
                    .waitFor();
            } else {
                System.out.print("\033[H\033[3J\033[2J");
                System.out.flush();
                
                new ProcessBuilder("tput", "reset")
                    .inheritIO()
                    .start()
                    .waitFor();
                
                System.out.print("\033[8;30;120t");
                System.out.flush();
            }
        } catch (Exception e) {
            try {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } catch (Exception ignored) {}
        }
    }
}
