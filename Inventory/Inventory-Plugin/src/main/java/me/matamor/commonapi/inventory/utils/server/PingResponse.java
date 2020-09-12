package me.matamor.commonapi.inventory.utils.server;

import lombok.Getter;
import me.matamor.commonapi.inventory.InventoryModule;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class PingResponse {

    @Getter
    private boolean isOnline;

    @Getter
    private String motd;

    @Getter
    private int onlinePlayers;

    @Getter
    private int maxPlayers;

    public PingResponse(boolean isOnline, String motd, int onlinePlayers, int maxPlayers) {
        this.isOnline = isOnline;
        this.motd = motd;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
    }

    public PingResponse(String jsonString, ServerAddress address) {
        if (jsonString == null || jsonString.isEmpty()) {
            this.motd = "Invalid ping response";
            InventoryModule.getInstance().getLogger().severe("Received empty Json response from IP \"" + address.toString() + "\"!");
            return;
        }

        Object jsonObject = JSONValue.parse(jsonString);

        if (!(jsonObject instanceof JSONObject)) {
            this.motd = "Invalid ping response";
            InventoryModule.getInstance().getLogger().severe("Received invalid Json response from IP \"" + address.toString() + "\": " + jsonString);
            return;
        }

        JSONObject json = (JSONObject) jsonObject;
        this.isOnline = true;

        Object descriptionObject = json.get("description");

        if (descriptionObject != null) {
            this.motd = descriptionObject.toString();
        } else {
            this.motd = "Invalid ping response";
            InventoryModule.getInstance().getLogger().severe("Received invalid Json response from IP \"" + address.toString() + "\": " + jsonString);
        }

        Object playersObject = json.get("players");

        if (playersObject instanceof JSONObject) {
            JSONObject playersJson = (JSONObject) playersObject;

            Object onlineObject = playersJson.get("online");
            if (onlineObject instanceof Number) {
                this.onlinePlayers = ((Number) onlineObject).intValue();
            }

            Object maxObject = playersJson.get("max");
            if (maxObject instanceof Number) {
                this.maxPlayers = ((Number) maxObject).intValue();
            }
        }
    }
}