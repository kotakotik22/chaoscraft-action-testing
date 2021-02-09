package com.kotakotik.chaoscraft.chaos_handlers;

public abstract class Credit {
    public static class Text extends Credit {
        private final String text;
        private final String displayName;

        public Text(String text, String displayName) {
            this.text = text;
            this.displayName = displayName;
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public String getDisplayName() {
            return displayName;
        }
    }

    public static class Youtube extends Text {
        public Youtube(String channel, String displayName) {
            super(channel, displayName);
        }

        @Override
        public String getText() {
            return "https://www.youtube.com/channel/" + super.getText();
        }
    }

    public abstract String getText();
    public abstract String getDisplayName();
}
