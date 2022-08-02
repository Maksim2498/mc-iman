package space.moontalk.mc.iman;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface PluginHolder {
    @NonNull Iman getPlugin();
}
