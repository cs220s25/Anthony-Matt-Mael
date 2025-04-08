package edu.moravian;

import edu.moravian.exceptions.SessionAlreadyInProgressException;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class TorkBot {

    private static final SessionManager sessionManager = new SessionManager();

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();
            String discordToken = dotenv.get("DISCORD_KEY");

            if (discordToken == null || discordToken.isBlank()) {
                throw new IllegalArgumentException("Discord token not found. Ensure DISCORD_KEY is set in the .env file.");
            }

            BotResponder responder = new BotResponder(new RedisManager());

            startBot(responder, discordToken);

            System.out.println("TorkBot is running...");
        } catch (LoginException e) {
            System.err.println("Failed to log in to Discord. Please check your bot token.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while starting the bot.");
            e.printStackTrace();
        }
    }

    private static void startBot(BotResponder responder, String token) throws LoginException {
        JDA api = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        api.addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(MessageReceivedEvent event) {
                if (event.getAuthor().isBot()) return;

                TextChannel channel = event.getChannel().asTextChannel();
                if (!channel.getName().equals("torktest")) return;

                String userId = event.getAuthor().getId();
                String message = event.getMessage().getContentRaw();

                // Check session management
                try {
                    if (!sessionManager.isSessionActive()) {
                        sessionManager.startSession(userId);
                        String response = responder.respond(event, userId, message);

                        if (response != null && !response.isBlank()) {
                            event.getChannel().sendMessage(response).queue();
                        }

                        System.out.println("User: " + event.getAuthor().getName() + ", Message: " + message + ", Response: " + response);

                        // End session after processing
                        sessionManager.endSession(userId);
                    } else {
                        String activeUser = sessionManager.getActiveSessionPlayer();
                        event.getChannel().sendMessage("Currently helping another user: " + activeUser + ". Please wait your turn.").queue();
                    }
                } catch (SessionAlreadyInProgressException e) {
                    event.getChannel().sendMessage("A session is already active. Please wait your turn.").queue();
                } catch (IllegalStateException e) {
                    System.err.println("Error ending session: " + e.getMessage());
                }
            }
        });
    }
}