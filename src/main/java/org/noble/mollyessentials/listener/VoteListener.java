package org.noble.mollyessentials.listener;

import com.bencodez.votingplugin.events.PlayerVoteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class VoteListener implements Listener {

    @EventHandler
    public void onPlayerVote(PlayerVoteEvent event) {
        String playerName = event.getPlayer();
        String serviceName = event.getServiceSite();

        Player player = Bukkit.getPlayerExact(playerName); // Get the Player object from the player's name

        if (player != null && player.isOnline()) {
            player.sendMessage(String.format("Thank you for voting on %s!", serviceName));
        }

        sendWebhook("https://discord.com/api/webhooks/1152138858532700210/YHVAXGYSDGtZEnm9sCaaAZfLk2D_RkeOtGAcYjKylJMyf2SODZDCshmCZZSYE28gVUzD", playerName, playerName + " has voted on " + serviceName);
    }

    private static final int[] COLORS = {
            16711680, // Red
            16776960, // Yellow
            65280,    // Green
            65535,    // Cyan
            255,      // Blue
            16711935  // Magenta
    };
    private static final Random RANDOM = new Random();

    private String getRealUUID(String playerName) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "MollyEssentials");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            StringBuilder result = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                result.append((char) character);
            }
            reader.close();

            String uuid = new JSONObject(result.toString()).getString("id");
            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendWebhook(String url, String playerName, String content) {
        try {
            String realUUID = getRealUUID(playerName);
            String avatarURL = (realUUID != null) ? "https://crafatar.com/avatars/" + realUUID + "?overlay" : "DEFAULT_IMAGE_URL_HERE";

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.addRequestProperty("Content-Type", "application/json");
            httpURLConnection.addRequestProperty("User-Agent", "MollyEssentials");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");

            int color = COLORS[RANDOM.nextInt(COLORS.length)];

            String jsonContent = "{" +
                    "\"embeds\": [{" +
                    "\"title\": \"🎉 Vote Received! 🎉\"," +  // Added celebration emojis around title
                    "\"description\": \"🗳️ " + content + " 🗳️\"," +  // Added ballot box emojis around content
                    "\"color\": " + color + "," +
                    "\"thumbnail\": {" +
                    "\"url\": \"" + avatarURL + "\"" +
                    "}" +
                    "}]" +
                    "}";

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonContent.getBytes("UTF-8"));
            outputStream.close();

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
