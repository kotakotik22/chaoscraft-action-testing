package com.kotakotik.chaoscraft.chaos_handlers;

public abstract class Credit {
    public static class Text extends Credit {
        private final String text;

        public Text(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
    }

    public static class Youtube extends Text {
        public Youtube(String text) {
            super(text);
        }

        @Override
        public String getText() {
            return "https://www.youtube.com/channel/" + super.getText();
        }
    }

    public abstract String getText();
}
