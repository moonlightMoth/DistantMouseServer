import java.util.HashMap;

public class CommandsMap extends HashMap<String, String>
{
    CommandsMap()
    {
        put("+v", "xdotool mousemove_relative 0 -1");
        put("-v", "xdotool mousemove_relative 0 1");
        put("+h", "xdotool mousemove_relative -1 0");
        put("-h", "xdotool mousemove_relative 1 0");
        put("1c", "xdotool click 1");
        put("2c", "xdotool click 2");
        put("3c", "xdotool click 3");
        put("4c", "xdotool click 4");
        put("5c", "xdotool click 5");
        put("qwe", "for i in {1..10}; do xdotool mousemove_relative 1 0; done");
    }
}
