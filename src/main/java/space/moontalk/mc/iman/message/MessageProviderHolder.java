package space.moontalk.mc.iman.message;

import org.jetbrains.annotations.NotNull;

public interface MessageProviderHolder {
    @NotNull MessageProvider getMessageProvider();
}
