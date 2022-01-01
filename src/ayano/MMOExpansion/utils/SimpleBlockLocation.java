package ayano.MMOExpansion.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Location;

public class SimpleBlockLocation {
  protected final String world;
  
  private final int x;
  
  private final int y;
  
  private final int z;
  
  public String getWorld() {
    return this.world;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public int getZ() {
    return this.z;
  }
  
  public SimpleBlockLocation(int x, int y, int z, String worldName) {
    this.world = worldName;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public SimpleBlockLocation(Location loc) {
    this(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName());
  }
  
  public SimpleBlockLocation(JsonObject json) {
    this.world = json.get("world").getAsString();
    JsonObject coords = json.get("coords").getAsJsonObject();
    this.x = coords.get("x").getAsInt();
    this.y = coords.get("y").getAsInt();
    this.z = coords.get("z").getAsInt();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj instanceof SimpleBlockLocation) {
      SimpleBlockLocation other = (SimpleBlockLocation)obj;
      return (other.getWorld().equals(getWorld()) && other.getX() == getX() && other.getY() == getY() && other.getZ() == getZ());
    } 
    return false;
  }
  
  public String toString() {
    return "SimpleBlockLocation{World='" + this.world + "',X='" + this.x + "',Y='" + this.y + "',Z='" + this.z + "'}";
  }
  
  public int hashCode() {
    return toString().hashCode();
  }
  
  public JsonElement toJson() {
    JsonObject json = new JsonObject();
    JsonObject coords = new JsonObject();
    coords.addProperty("x", Integer.valueOf(this.x));
    coords.addProperty("y", Integer.valueOf(this.y));
    coords.addProperty("z", Integer.valueOf(this.z));
    json.addProperty("world", this.world);
    json.add("coords", (JsonElement)coords);
    return (JsonElement)json;
  }
}
