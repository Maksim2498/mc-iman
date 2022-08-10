package space.moontalk.mc.iman;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

import net.kyori.adventure.text.Component;

@Getter
@AllArgsConstructor
public class ComponentException extends Exception {
    @NotNull
    private final Component component;
}
