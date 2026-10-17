package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.game.SendMessageEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class ModuleExample extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");

    private final Setting<Boolean> useRlo = sgGeneral.add(new BoolSetting.Builder()
        .name("use-rlo")
        .description("use rlo bypass")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> useTransliterate = sgGeneral.add(new BoolSetting.Builder()
        .name("transliterate")
        .description("use unicode lookalikes")
        .defaultValue(true)
        .build());

    private final Setting<String> command = sgGeneral.add(new StringSetting.Builder()
        .name("command")
        .description("command to send messages with, leave empty to disable")
        .defaultValue("/minecraft:me")
        .build());

    public ModuleExample() {
        super(AddonTemplate.CATEGORY, "chat-bypass", "Attempt to bypass chat filters");
    }

    @EventHandler
    private void onSendMessage(SendMessageEvent e) {
        String message = e.message;
        if (useTransliterate.get()) {
            message = substitute(message);
        }
        if (useRlo.get()) {
            message = rlo(message);
        }
        if (!command.get().isBlank()) {
            ChatUtils.sendPlayerMsg("/" + command.get() + " " + message, false);
            e.cancel();
        }
        e.message = message;
    }

  private static String substitute(String msg) {
        StringBuilder rval = new StringBuilder(msg);

        for (int i = 0; i < msg.length(); i++) {
          switch(msg.charAt(i)) {
            case 'a':
              rval.setCharAt(i,'а');
              break;
            case 'A':
              rval.setCharAt(i,'А');
              break;
            case 'e':
              rval.setCharAt(i,'е');
              break;
            case 'E':
              rval.setCharAt(i,'Е');
              break;
            case 'M':
              rval.setCharAt(i,'М');
              break;
            case 'o':
              rval.setCharAt(i,'о');
              break;
            case 'O':
              rval.setCharAt(i,'О');
              break;
            case 'c':
              rval.setCharAt(i,'с');
              break;
            case 'C':
              rval.setCharAt(i,'С');
              break;
            case '.':
              rval.setCharAt(i,'̣');
              break;
            case 'Z':
              rval.setCharAt(i,'Ζ');
              break;
            case 'N':
              rval.setCharAt(i,'Ν');
              break;
            case 'h':
              rval.setCharAt(i,'һ');
              break;
            case 'y':
              rval.setCharAt(i,'у');
              break;
            case 'n':
              rval.setCharAt(i,'ո');
              break;
            default:
                break;
          }
        }

        return rval.toString();
      }

  private static String rlo(String msg) {
    // following line contains the RLO
    // if it looks reversed, that's why
    msg += "";
    msg = new StringBuilder(msg).reverse().toString();
    return msg;
  }
}
