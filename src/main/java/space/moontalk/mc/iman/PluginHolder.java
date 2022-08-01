package space.moontalk.mc.iman;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PluginHolder {
    @NonNull
    final Iman plugin;
}
