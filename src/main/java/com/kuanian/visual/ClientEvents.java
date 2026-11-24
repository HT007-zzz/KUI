package com.kuanian.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KuanianVisual.MODID, value = Dist.CLIENT)
public final class ClientEvents {
    private static int ticks;

    private ClientEvents() {
    }

    /** 游戏会在进出世界时把窗口标题改回去，所以隔一会儿重新设置一次。 */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (++ticks % 10 == 0) {
            Minecraft.getInstance().getWindow().setTitle(KuanianVisual.WINDOW_TITLE);
        }
    }

    /** 把主界面的「退出游戏」按钮改成自定义文字。 */
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen)) {
            return;
        }
        for (GuiEventListener listener : event.getListenersList()) {
            if (listener instanceof Button button
                    && button.getMessage().getContents() instanceof TranslatableContents contents
                    && "menu.quit".equals(contents.getKey())) {
                button.setMessage(Component.literal(KuanianVisual.QUIT_TEXT));
            }
        }
    }

    /** 主界面渲染完成后，用自己的背景、标题和按钮样式盖在上面。 */
    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof TitleScreen screen) {
            TitleStyle.render(event.getGuiGraphics(), screen, event.getMouseX(), event.getMouseY(), event.getPartialTick());
        }
    }
}
