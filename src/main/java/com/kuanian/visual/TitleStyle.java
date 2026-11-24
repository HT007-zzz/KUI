package com.kuanian.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

/** 白 + 蓝的解构风主界面：错位的色块、细线、刻度，字母本身保持完整。 */
final class TitleStyle {
    static final int PAPER = 0xFFF3F6FC;
    static final int WHITE = 0xFFFFFFFF;
    static final int BLUE = 0xFF2457FF;
    static final int DEEP = 0xFF0B2A8A;
    static final int SKY = 0xFFCFE0FF;
    static final int MID = 0xFF8FB0FF;
    static final int FAINT = 0x142457FF;
    static final int GRAY = 0xFF9AA9CC;

    private TitleStyle() {
    }

    static void render(GuiGraphics g, TitleScreen screen, int mx, int my, float partialTick) {
        Font font = Minecraft.getInstance().font;
        int w = screen.width;
        int h = screen.height;
        float t = (Util.getMillis() % 3_600_000L) / 1000f;

        background(g, w, h, t);
        logo(g, font, w, h, t);
        widgets(g, font, screen, mx, my, partialTick);
    }

    private static void background(GuiGraphics g, int w, int h, float t) {
        g.fill(0, 0, w, h, PAPER);

        // 淡淡的网格，缓慢横移
        int off = (int) ((t * 5f) % 24f);
        for (int x = -24 + off; x < w; x += 24) {
            g.fill(x, 0, x + 1, h, FAINT);
        }
        for (int y = 0; y < h; y += 24) {
            g.fill(0, y, w, y + 1, FAINT);
        }

        // 左侧蓝色大块，带一层错位的浅蓝，像套印没对准
        int lw = Math.max(18, (int) (w * 0.11f));
        int lx = (int) (w * 0.05f + Math.sin(t * 0.5f) * w * 0.01f);
        int ly = (int) (h * 0.32f);
        int lh = (int) (h * (0.34f + 0.04f * Math.sin(t * 0.7f)));
        g.fill(lx + 5, ly + 5, lx + lw + 5, ly + lh + 5, SKY);
        g.fill(lx, ly, lx + lw, ly + lh, BLUE);

        // 右上浅蓝宽块，横向漂移，压在标题右端后面
        int rw = (int) (w * 0.30f);
        int rx = (int) (w * 0.64f + Math.sin(t * 0.35f) * w * 0.02f);
        int ry = (int) (h * 0.07f);
        int rh = (int) (h * 0.14f);
        g.fill(rx, ry, rx + rw, ry + rh, SKY);
        g.fill(rx + 8, ry + rh - 3, rx + rw - 8, ry + rh, MID);

        // 右侧深蓝窄条，上下滑动
        int bw = Math.max(4, (int) (w * 0.012f));
        int bx = w - (int) (w * 0.07f);
        int bh = (int) (h * 0.30f);
        int by = (int) (h * 0.45f + Math.sin(t * 0.6f) * h * 0.08f);
        g.fill(bx, by, bx + bw, by + bh, DEEP);

        // 标题与按钮之间的细线 + 缓慢移动的刻度
        int hy = h / 4 + 30;
        g.fill(0, hy, w, hy + 1, BLUE);
        int tick = (int) ((t * 14f) % 16f);
        for (int x = -16 + tick; x < w; x += 16) {
            g.fill(x, hy + 1, x + 1, hy + 4, BLUE);
        }

        // 底部左侧滚动虚线
        int dy = h - 18;
        int dash = (int) ((t * 24f) % 12f);
        int limit = (int) (w * 0.45f);
        for (int x = -12 + dash; x < limit; x += 12) {
            int x1 = Math.max(0, x);
            int x2 = Math.max(0, x + 6);
            g.fill(x1, dy, x2, dy + 1, BLUE);
        }
        g.fill(0, h - 12, (int) (w * 0.18f), h - 9, BLUE);

        // 几个缓慢漂浮的空心方框
        for (int i = 0; i < 3; i++) {
            float ph = t * (0.4f + i * 0.15f) + i * 2.1f;
            int s = 12 + i * 6;
            int cx = (int) (w * (0.80f + 0.05f * i) + Math.cos(ph) * w * 0.02f);
            int cy = (int) (h * (0.55f + 0.12f * i) + Math.sin(ph) * h * 0.03f);
            outline(g, cx, cy, cx + s, cy + s, 0xAA2457FF);
        }
    }

    private static void outline(GuiGraphics g, int x1, int y1, int x2, int y2, int color) {
        g.fill(x1, y1, x2, y1 + 1, color);
        g.fill(x1, y2 - 1, x2, y2, color);
        g.fill(x1, y1, x1 + 1, y2, color);
        g.fill(x2 - 1, y1, x2, y2, color);
    }

    private static void logo(GuiGraphics g, Font font, int w, int h, float t) {
        Component text = Component.literal(KuanianVisual.LOGO_TEXT).withStyle(ChatFormatting.BOLD);
        int fw = Math.max(1, font.width(text));
        int x0 = Math.max(12, w / 2 - 100);
        int top = 14;
        int avail = h / 4 + 20 - top;

        float s = Math.min((w - x0 - 12) / (float) fw, avail / 9f);
        s = Math.max(1.5f, Math.min(s, 10f));

        int y0 = (int) (top + (avail - 8 * s) / 2f + Math.sin(t * 0.8f) * 1.5f);

        PoseStack pose = g.pose();

        // 错位的浅蓝副本
        pose.pushPose();
        pose.translate(x0 + Math.max(2f, s * 0.4f), y0 + Math.max(2f, s * 0.4f), 0f);
        pose.scale(s, s, 1f);
        g.drawString(font, text, 0, 0, MID, false);
        pose.popPose();

        // 主体
        pose.pushPose();
        pose.translate(x0, y0, 0f);
        pose.scale(s, s, 1f);
        g.drawString(font, text, 0, 0, DEEP, false);
        pose.popPose();

        // 标题下方一条会伸缩的蓝条
        int uy = y0 + (int) (8 * s) + 5;
        int uw = (int) (fw * s * (0.30f + 0.10f * Math.sin(t * 1.1f)));
        g.fill(x0, uy, x0 + uw, uy + 3, BLUE);
    }

    private static void widgets(GuiGraphics g, Font font, TitleScreen screen, int mx, int my, float partialTick) {
        for (GuiEventListener listener : screen.children()) {
            if (!(listener instanceof AbstractWidget widget) || !widget.visible) {
                continue;
            }
            if (widget instanceof ImageButton) {
                widget.render(g, mx, my, partialTick);
                continue;
            }
            if (widget instanceof PlainTextButton) {
                g.drawString(font, widget.getMessage().getString(), widget.getX(),
                        widget.getY() + (widget.getHeight() - 8) / 2, GRAY, false);
                continue;
            }
            flatButton(g, font, widget);
        }
    }

    private static void flatButton(GuiGraphics g, Font font, AbstractWidget widget) {
        int x = widget.getX();
        int y = widget.getY();
        int bw = widget.getWidth();
        int bh = widget.getHeight();
        boolean active = widget.active;
        boolean hot = active && widget.isHoveredOrFocused();

        if (hot) {
            g.fill(x + 2, y + 2, x + bw + 2, y + bh + 2, DEEP);
            g.fill(x, y, x + bw, y + bh, BLUE);
        } else {
            g.fill(x, y, x + bw, y + bh, WHITE);
            outline(g, x, y, x + bw, y + bh, active ? BLUE : MID);
            g.fill(x, y, x + 3, y + bh, active ? BLUE : MID);
        }

        Component label = widget.getMessage();
        int color = hot ? WHITE : (active ? DEEP : GRAY);
        int tx = x + (bw - font.width(label)) / 2;
        int ty = y + (bh - 8) / 2;
        g.drawString(font, label, tx, ty, color, false);
    }
}
