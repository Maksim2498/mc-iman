package space.moontalk.mc.iman;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

import net.kyori.adventure.text.Component;

@Getter
@AllArgsConstructor
public class ComponentException extends Exception {
    @NonNull
    private final Component component;
}
