package ayano.MMOExpansion.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import ayano.MMOExpansion.MMOExpansion;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class xpansion extends PlaceholderExpansion {

	private MMOExpansion plugin = MMOExpansion.getPlugin();

    
    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }
    /**
     * Since this expansion requires api access to the plugin "SomePlugin" 
     * we must check if said plugin is on the server or not.
     *
     * @return true or false depending on if the required plugin is installed.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * 
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "a-simple-dev";
    }
 
    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest 
     * method to obtain a value if a placeholder starts with our 
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "mex";
    }

    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }
  
    /**
     * This is the method called when a placeholder with our identifier 
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.Player Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    
    
    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        if(identifier.startsWith("math_plus_")){
            String removed = PlaceholderAPI.setPlaceholders(player, identifier.replace("math_plus_", "").replace("(", "%").replace(")", "%"));
	            String[] result = removed.split("~");
	            Double val = Double.valueOf(result[0]) + Double.valueOf(result[1]);
	            return val.toString();
        }

        if(identifier.startsWith("math_minus_")){
            String removed = PlaceholderAPI.setPlaceholders(player, identifier.replace("math_minus_", "").replace("(", "%").replace(")", "%"));
	            String[] result = removed.split("~");
	            Double val = Double.valueOf(result[0]) - Double.valueOf(result[1]);
	            return val.toString();
        }

        if(identifier.startsWith("math_division_")){
            String removed = PlaceholderAPI.setPlaceholders(player, identifier.replace("math_division_", "").replace("(", "%").replace(")", "%"));
	            String[] result = removed.split("~");
	            Double val = Double.valueOf(result[0]) / Double.valueOf(result[1]);
	            return val.toString();
        }

        if(identifier.startsWith("math_multiply_")){
            String removed = PlaceholderAPI.setPlaceholders(player, identifier.replace("math_multiply_", "").replace("(", "%").replace(")", "%"));
	            String[] result = removed.split("~");
	            Double val = Double.valueOf(result[0]) * Double.valueOf(result[1]);
	            return val.toString();
        }

        if(identifier.startsWith("math_percent_")){
            String removed = PlaceholderAPI.setPlaceholders(player, identifier.replace("math_percent_", "").replace("(", "%").replace(")", "%"));
	            String[] result = removed.split("~");
	            Double val = ( Double.valueOf(result[0]) / Double.valueOf(result[1]) ) * 100;
	            return val.toString();
        }
        
        if(identifier.startsWith("math_round_")) {
            String removed = PlaceholderAPI.setPlaceholders(player, identifier.replace("math_round_", "").replace("(", "%").replace(")", "%"));
            Double d = new Double(removed);
            int i= (int) Math.round(d);
            return Integer.valueOf(i).toString();
        	
        }

        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%) 
        // was provided
        return null;
    }
}
