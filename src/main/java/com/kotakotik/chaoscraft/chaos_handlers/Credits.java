package com.kotakotik.chaoscraft.chaos_handlers;

public enum Credits {
    HUSKER(new Credit.Youtube("UCwUc0K8jbiUu-Q0OjwoCUpQ")),
    PHYSICALLY(new Credit.Youtube("UCe_o7-f-GNiFoY9CfepYTXQ"));

    public final Credit credit;

    Credits(Credit credit) {
        this.credit = credit;
    }
}
