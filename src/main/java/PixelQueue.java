import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        name = "PixelQueue",
        version = "0.1",
        authors = "itsyxD",
        id = "1.0"

)
public class PixelQueue {

    public static PixelQueue instace;


    @Listener
    public void onEnable(GameInitializationEvent e){

    }

    @Listener
    public void onDisable(GameStoppedServerEvent e){
        instace = this;


    }

    public static PixelQueue getInstance(){return instace;}


}
